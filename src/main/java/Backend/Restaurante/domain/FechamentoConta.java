package Backend.Restaurante.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "fechamentos_conta")
public class FechamentoConta {
    @Id
    @GeneratedValue
    private long id;


    private BigDecimal subtotal;

    @Column(name = "taxa_servico")
    private BigDecimal taxaServico;

    private BigDecimal desconto;
    private BigDecimal total;

    @Column(name = "data_fechamento")
    private LocalDateTime dataFechamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;


    @PrePersist
    public void prePersist(){
        this.dataFechamento = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTaxaServico() {
        return taxaServico;
    }

    public void setTaxaServico(BigDecimal taxaServico) {
        this.taxaServico = taxaServico;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDateTime getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(LocalDateTime dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
