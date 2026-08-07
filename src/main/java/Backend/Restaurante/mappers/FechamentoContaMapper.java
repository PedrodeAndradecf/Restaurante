package Backend.Restaurante.mappers;

import Backend.Restaurante.domain.FechamentoConta;
import Backend.Restaurante.dto.response.FechamentoContaResponse;

public class FechamentoContaMapper {

    private FechamentoContaMapper(){};

    public static FechamentoContaResponse toResponse(FechamentoConta fechamento){
        return new FechamentoContaResponse(
                fechamento.getId(),
                fechamento.getPedido().getId(),
                fechamento.getPedido().getMesa().getNumero(),
                fechamento.getSubtotal(),
                fechamento.getTaxaServico(),
                fechamento.getDesconto(),
                fechamento.getTotal(),
                fechamento.getDataFechamento()
        );

    }


}
