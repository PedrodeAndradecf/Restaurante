package Backend.Restaurante.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FechamentoContaResponse(
        Long id,
        Long pedidoId,
        Integer numeroMesa,
        BigDecimal subtotal,
        BigDecimal taxaServico,
        BigDecimal desconto,
        BigDecimal total,
        LocalDateTime dataFechamento
) {
}
