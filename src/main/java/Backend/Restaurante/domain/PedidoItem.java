package Backend.Restaurante.domain;

import Backend.Restaurante.domain.enums.StatusItemPedido;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="pedido_itens")
public class PedidoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Integer quantidade;

    @Column(name = "preco_unitario")
    private BigDecimal precoUnitario;

    private String observacao;

    @Enumerated(EnumType.STRING)
    private StatusItemPedido status = StatusItemPedido.PENDENTE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Column(name ="data_inicio_preparo")
    private LocalDateTime dataInicioPreparo;
    @Column(name ="data_produto")
    private LocalDateTime dataProduto;
    @Column(name ="data_entrega")
    private LocalDateTime dataEntrega;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public StatusItemPedido getStatus() {
        return status;
    }

    public void setStatus(StatusItemPedido status) {
        this.status = status;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public LocalDateTime getDataInicioPreparo() {
        return dataInicioPreparo;
    }

    public void setDataInicioPreparo(LocalDateTime dataInicioPreparo) {
        this.dataInicioPreparo = dataInicioPreparo;
    }

    public LocalDateTime getDataProduto() {
        return dataProduto;
    }

    public void setDataProduto(LocalDateTime dataProduto) {
        this.dataProduto = dataProduto;
    }

    public LocalDateTime getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDateTime dataEntrega) {
        this.dataEntrega = dataEntrega;
    }
}
