package Backend.Restaurante.dto.response;

public record PagamentoResponse(
        String status,
        String codigoTransacao
) {
}
