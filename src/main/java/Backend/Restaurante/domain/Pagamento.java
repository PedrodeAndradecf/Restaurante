package Backend.Restaurante.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "pagamentos")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    
}
