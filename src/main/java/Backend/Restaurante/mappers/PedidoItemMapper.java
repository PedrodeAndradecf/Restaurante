package Backend.Restaurante.mappers;

import Backend.Restaurante.domain.PedidoItem;
import Backend.Restaurante.dto.response.PedidoItemResponse;

import java.math.BigDecimal;

public class PedidoItemMapper {

    private PedidoItemMapper(){}



    public static PedidoItemResponse toResponse(PedidoItem item){

        BigDecimal total = item.getPrecoUnitario()
                .multiply(BigDecimal.valueOf(item.getQuantidade()));

        return new PedidoItemResponse(
                item.getId(),
                item.getPedido().getId(),
                item.getProduto().getId(),
                item.getProduto().getNome(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                total,
                item.getObservacao(),
                item.getStatus()
        );
    }
}
