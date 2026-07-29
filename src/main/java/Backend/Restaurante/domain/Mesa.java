package Backend.Restaurante.domain;

import Backend.Restaurante.domain.enums.StatusMesa;
import jakarta.persistence.*;

@Entity
@Table(name = "mesas")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numero;

    private String descricao;

    private Integer capacidade;

    @Enumerated(EnumType.STRING)
    private StatusMesa status = StatusMesa.LIVRE;

    public StatusMesa getStatus() {
        return status;
    }

    public void setStatus(StatusMesa status) {
        this.status = status;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
