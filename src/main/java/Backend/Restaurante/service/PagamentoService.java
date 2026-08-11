package Backend.Restaurante.service;

import Backend.Restaurante.client.PagamentoClient;
import Backend.Restaurante.domain.FechamentoConta;
import Backend.Restaurante.domain.Mesa;
import Backend.Restaurante.domain.Pagamento;
import Backend.Restaurante.domain.Pedido;
import Backend.Restaurante.domain.enums.FormaPagamento;
import Backend.Restaurante.domain.enums.StatusMesa;
import Backend.Restaurante.domain.enums.StatusPagamento;
import Backend.Restaurante.domain.enums.StatusPedido;
import Backend.Restaurante.dto.request.PagamentoRequest;
import Backend.Restaurante.dto.response.PagamentoResponse;
import Backend.Restaurante.exception.RegraNegocioException;
import Backend.Restaurante.repository.FechamentoContaRepository;
import Backend.Restaurante.repository.MesaRepository;
import Backend.Restaurante.repository.PagamentoRepository;
import Backend.Restaurante.repository.PedidoRepository;
import jakarta.transaction.Transactional;

public class PagamentoService {

    private final PagamentoClient pagamentoClient;
    private final FechamentoContaRepository fechamentoRepository;
    private final PedidoRepository pedidoRepository;
    private final MesaRepository mesaRepository;
    private final PagamentoRepository pagamentoRepository;



    public PagamentoService(
            PagamentoClient pagamentoClient,
            FechamentoContaRepository fechamentoRepository,
            PedidoRepository pedidoRepository,
            MesaRepository mesaRepository,
            PagamentoRepository pagamentoRepository
    ){
        this.pagamentoClient = pagamentoClient;
        this.fechamentoRepository = fechamentoRepository;
        this.pedidoRepository = pedidoRepository;
        this.mesaRepository = mesaRepository;
        this.pagamentoRepository = pagamentoRepository;
    }


    @Transactional
    public void pagar (Long pedidoId, String formaPagamento){
        FechamentoConta fechamento = fechamentoRepository.findByPedidoId(pedidoId)
                .orElseThrow( () -> new RegraNegocioException("Conta não encontrada"));


        PagamentoResponse response = pagamentoClient.processar(
                new PagamentoRequest(
                        fechamento.getTotal().doubleValue(),
                        formaPagamento
                )
        );

        if( "APROVADO".equals(response.status())){
            Pedido pedido = fechamento.getPedido();
            pedido.setStatus(StatusPedido.FECHADO);


            Mesa mesa = pedido.getMesa();
            mesa.setStatus(StatusMesa.INATIVA);

            Pagamento pagamento = new Pagamento();

            pagamento.setPedido(pedido);
            pagamento.setFormaPagamento(FormaPagamento.getFormaPagamento(formaPagamento));
            pagamento.setValor(fechamento.getTotal());
            pagamento.setStatusPagamento(StatusPagamento.APROVADO);
            pagamento.setDataPagamento(fechamento.getDataFechamento());

            pedidoRepository.save(pedido);

            mesaRepository.save(mesa);
            pagamentoRepository.save(pagamento);

        }
    }


}
