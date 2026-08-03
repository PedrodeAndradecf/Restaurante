package Backend.Restaurante.dto.request;

import java.math.BigDecimal;

public record ProdutoRequest(
        long categoriaId,
        String nome,
        String descricao,
        BigDecimal preco,
        Boolean disponivel,
        Integer tempoPreparoMinutos
) {
}
