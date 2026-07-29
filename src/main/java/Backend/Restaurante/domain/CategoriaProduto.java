package Backend.Restaurante.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias_produtos")
public class CategoriaProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Boolean ativa = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
