package Backend.Restaurante.mappers;

import Backend.Restaurante.domain.PedidoItem;
import Backend.Restaurante.dto.response.CozinhaItemResponse;

public class CozinhaItemMapper {

    private CozinhaItemMapper(){};



    public static CozinhaItemResponse toResponse(PedidoItem item){
        return new CozinhaItemResponse(
                item.getId(),
                item.getPedido().getId(),
                item.getPedido().getMesa().getNumero(),
                item.getProduto().getNome(),
                item.getQuantidade(),
                item.getObservacao(),
                item.getPrecoUnitario(),
                item.getStatus()
        );
    }
}
