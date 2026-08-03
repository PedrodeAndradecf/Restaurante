package Backend.Restaurante.repository;

import Backend.Restaurante.domain.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

}
