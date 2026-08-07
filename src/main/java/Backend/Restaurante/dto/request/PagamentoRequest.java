package Backend.Restaurante.dto.request;

public record PagamentoRequest(
        Double valor,
        String formaPagamento
) {
}
