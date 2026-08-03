package Backend.Restaurante.projection;

import java.math.BigDecimal;

public interface ProdutoMaisVendidoProjection {
    Long getProdutoId();
    String getProdutoNome();
    Long getQuantidadeVendida();
    BigDecimal getTotalVendido();
}
