package Backend.Restaurante.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProdutoResponse(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Boolean disponivel,
        Integer tempoPreparoMinutos,
        Long categoriaId,
        String categoriaNome,
        LocalDateTime criadoEm
) {
}
