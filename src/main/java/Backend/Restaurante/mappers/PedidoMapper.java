package Backend.Restaurante.mappers;

import Backend.Restaurante.domain.Pedido;
import Backend.Restaurante.dto.response.PedidoResponse;

public class PedidoMapper {

    private PedidoMapper(){};

    public static PedidoResponse toResponse(Pedido pedido){
        return new PedidoResponse(
                pedido.getId(),
                pedido.getMesa().getId(),
                pedido.getMesa().getNumero(),
                pedido.getDataAbertura(),
                pedido.getDataFechamento(),
                pedido.getStatus(),
                pedido.getObservacao()
        );
    }
}
