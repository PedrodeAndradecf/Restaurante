package Backend.Restaurante.service;

import Backend.Restaurante.domain.Mesa;
import Backend.Restaurante.domain.Pedido;
import Backend.Restaurante.domain.PedidoItem;
import Backend.Restaurante.domain.Produto;
import Backend.Restaurante.domain.enums.StatusItemPedido;
import Backend.Restaurante.domain.enums.StatusMesa;
import Backend.Restaurante.domain.enums.StatusPedido;
import Backend.Restaurante.dto.request.PedidoItemRequest;
import Backend.Restaurante.dto.request.PedidoRequest;
import Backend.Restaurante.dto.response.PedidoItemResponse;
import Backend.Restaurante.dto.response.PedidoResponse;
import Backend.Restaurante.exception.RegraNegocioException;
import Backend.Restaurante.mappers.PedidoItemMapper;
import Backend.Restaurante.mappers.PedidoMapper;
import Backend.Restaurante.repository.MesaRepository;
import Backend.Restaurante.repository.PedidoItemRepository;
import Backend.Restaurante.repository.PedidoRepository;
import Backend.Restaurante.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PagamentoService {

    private final PedidoRepository pedidoRepository;
    private final MesaRepository mesaRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoItemRepository pedidoItemRepository;



    public PagamentoService(PedidoRepository pedidoRepository,
                            MesaRepository mesaRepository,
                            ProdutoRepository produtoRepository,
                            PedidoItemRepository pedidoItemRepository){
        this.pedidoRepository = pedidoRepository;
        this.mesaRepository = mesaRepository;
        this.produtoRepository = produtoRepository;
        this.pedidoItemRepository = pedidoItemRepository;

    }

    public PedidoResponse abirPedido(PedidoRequest request){
        Mesa mesa = mesaRepository.findById(request.mesaId())
                .orElseThrow(() -> new RegraNegocioException("Mesa não encontrada"));


        if (mesa.getStatus() != StatusMesa.LIVRE){
            throw new RegraNegocioException("A mesa ocupada, portanto não foi possivel abrir o pedido");
        }


        Pedido pedido = new Pedido();
        pedido.setMesa(mesa);
        pedido.setStatus(StatusPedido.ABERTO);
        pedido.setObservacao(request.observacao());

        mesa.setStatus(StatusMesa.OCUPADA);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        mesaRepository.save(mesa);

        return PedidoMapper.toResponse(pedidoSalvo);
    }

    public Page<PedidoResponse> listar(Pageable pageable){
        return pedidoRepository.findAll(pageable)
                .map(PedidoMapper::toResponse);
    }

    public Pedido buscarPedidoPorId(long pedidoId){
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RegraNegocioException("Pedido não encontrado"));

    }

    public PedidoResponse buscarPorId(Long pedidoId){
        Pedido pedido = buscarPedidoPorId(pedidoId);

        return PedidoMapper.toResponse(pedido);
    }


    public PedidoItemResponse adicionarItem(Long pedidoId, PedidoItemRequest request){
        Pedido pedido = buscarPedidoPorId(pedidoId);


        if(pedido.getStatus() != StatusPedido.ABERTO){
            throw new RegraNegocioException("Só é possivel adicionar itens em pedidos abertos");
        }

        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new RegraNegocioException("Produto não encontrado"));

        if (!produto.getDisponivel()){
            throw new RegraNegocioException("Produto indisponivel no Cardapio");
        }

        if(request.quantidade() == null || request.quantidade() <= 0){
            throw new RegraNegocioException("Acrescente uma unidade para que o pedido seja efetuado");
        }

        PedidoItem item = new PedidoItem();
        item.setPedido(pedido);
        item.setProduto(produto);
        item.setQuantidade(request.quantidade());
        item.setPrecoUnitario(produto.getPreco());
        item.setObservacao(request.observacao());
        item.setStatus(StatusItemPedido.PENDENTE);


        return PedidoItemMapper.toResponse(item);
    }

    public List<PedidoItemResponse> listarItens(Long pedidoId){

        List<PedidoItemResponse> listaItens = pedidoItemRepository.findByPedidoId(pedidoId).stream()
                .map(PedidoItemMapper::toResponse).toList();

        return listaItens;
    }
}
