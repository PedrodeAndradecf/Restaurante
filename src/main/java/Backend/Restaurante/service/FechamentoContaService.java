package Backend.Restaurante.service;

import Backend.Restaurante.domain.FechamentoConta;
import Backend.Restaurante.domain.Pedido;
import Backend.Restaurante.domain.PedidoItem;
import Backend.Restaurante.domain.enums.StatusItemPedido;
import Backend.Restaurante.domain.enums.StatusPedido;
import Backend.Restaurante.dto.request.FechamentoContaRequest;
import Backend.Restaurante.dto.response.FechamentoContaResponse;
import Backend.Restaurante.exception.RegraNegocioException;
import Backend.Restaurante.mappers.FechamentoContaMapper;
import Backend.Restaurante.repository.FechamentoContaRepository;
import Backend.Restaurante.repository.PedidoItemRepository;
import Backend.Restaurante.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FechamentoContaService {

    private final PedidoRepository pedidoRepository;
    private final PedidoItemRepository pedidoItemRepository;
    private final FechamentoContaRepository fechamentoContaRepository;



    public FechamentoContaService(
            PedidoRepository pedidoRepository,
            PedidoItemRepository pedidoItemRepository,
            FechamentoContaRepository fechamentoContaRepository
    ){
        this.pedidoRepository = pedidoRepository;
        this.pedidoItemRepository = pedidoItemRepository;
        this.fechamentoContaRepository = fechamentoContaRepository;
    }


    @Transactional
    public FechamentoContaResponse fecharConta(Long pedidoId, FechamentoContaRequest request){
        Pedido pedido = buscarPorPedidoId(pedidoId);

        if (pedido.getStatus() == StatusPedido.FECHADO){
            throw new RegraNegocioException("O Pedido já esta fechado");
        }

        if(pedido.getStatus() == StatusPedido.CANCELADO){
            throw new RegraNegocioException("O pedido foi cancelado, portanto não pode ser fechado");
        }

        if (fechamentoContaRepository.existsByPedidoId(pedidoId)){
            throw new RegraNegocioException("Já existe fechamento para esse pedido");
        }

        List<PedidoItem> itens = pedidoItemRepository.findByPedidoId(pedidoId);

        if (itens.isEmpty()){
            throw new RegraNegocioException("A conta não possui itens");
        }

        List<PedidoItem> itensNaoEntregues = pedidoItemRepository.findByPedidoIdAndStatusNot(pedidoId, StatusItemPedido.ENTREGUE);

        if (!itensNaoEntregues.isEmpty()){
            throw new RegraNegocioException("Existem itens não entregues, portanto não é possivel fehar a conta");
        }

        BigDecimal subtotal = itens.stream()
                .map(item -> item.getPrecoUnitario().multiply((BigDecimal.valueOf(item.getQuantidade()))))
                .reduce(BigDecimal.ZERO, BigDecimal::add);




        BigDecimal taxaServico = request.taxaServico() != null ? request.taxaServico() : BigDecimal.ZERO;
        BigDecimal desconto = request.desconto() != null ? request.desconto() : BigDecimal.ZERO;

        if (taxaServico.compareTo(BigDecimal.ZERO) < 0){
            throw new RegraNegocioException("Taxa de serviço não pode ser negativa");
        }

        if (desconto.compareTo(BigDecimal.ZERO) < 0){
            throw new RegraNegocioException("Desconto não pode ser negativo");
        }

        BigDecimal total = subtotal.add(taxaServico).subtract(desconto);

        if (total.compareTo(BigDecimal.ZERO) < 0){
            throw new RegraNegocioException("Total não pode ser negativo");
        }

        FechamentoConta fechamento = new FechamentoConta();
        fechamento.setPedido(pedido);
        fechamento.setSubtotal(subtotal);
        fechamento.setTaxaServico(taxaServico);
        fechamento.setDesconto(desconto);
        fechamento.setTotal(total);

        pedido.setStatus(StatusPedido.FECHADO);
        pedido.setDataFechamento(LocalDateTime.now()); 

        FechamentoConta fechamentoSalvo = fechamentoContaRepository.save(fechamento);
        pedidoRepository.save(pedido);

        return FechamentoContaMapper.toResponse(fechamentoSalvo);

    }


    public FechamentoContaResponse buscarPorPedido(Long pedidoId){
        FechamentoConta fechamento = fechamentoContaRepository.findPedidoId(pedidoId)
                .orElseThrow( () -> new RegraNegocioException("Pedido não encontrado"));

        return FechamentoContaMapper.toResponse(fechamento);

    }

    private Pedido buscarPorPedidoId(Long pedidoId){
        return pedidoRepository.findById(pedidoId)
                .orElseThrow( () -> new RegraNegocioException("Pedido não encontrado"));
    }
}
