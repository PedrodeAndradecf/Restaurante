package Backend.Restaurante.projection;

import java.math.BigDecimal;

public interface FaturamentoDiarioProjection {
    String getData();
    BigDecimal getTotal();
}
