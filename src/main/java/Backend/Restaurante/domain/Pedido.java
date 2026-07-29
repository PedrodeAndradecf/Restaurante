package Backend.Restaurante.domain;

import Backend.Restaurante.domain.enums.StatusPedido;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataAbertura;

    private LocalDateTime dataFechamento;

    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.ABERTO;

    private String observacao;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mesa_id")
    private Mesa mesa;


    @PrePersist
    public void prePersist(){
        this.dataAbertura = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public LocalDateTime getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(LocalDateTime dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }
}
