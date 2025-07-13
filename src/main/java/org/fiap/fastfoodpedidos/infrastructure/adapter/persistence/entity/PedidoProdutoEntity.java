package org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Entity
@Table(name = "pedido_produto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoProdutoEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Setter
    @NotNull
    @Column(name = "produto_id", nullable = false)
    private Integer produtoId;

    @Setter
    @NotNull
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @NotNull
    @Column(name = "valor_unitario_momento_venda", nullable = false)
    private BigDecimal valorUnitarioProdutoMomentoVenda;

    @Setter
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @JsonIgnoreProperties(value = {"pedidoProdutos", "cliente", "produtos"}, allowSetters = true)
    private PedidoEntity pedido;

    public PedidoProdutoEntity id(Integer id) {
        this.setId(id);
        return this;
    }

    public PedidoProdutoEntity quantidade(Integer quantidade) {
        this.setQuantidade(quantidade);
        return this;
    }

    public PedidoProdutoEntity pedido(PedidoEntity pedidoEntity) {
        this.setPedido(pedidoEntity);
        return this;
    }

    public PedidoProdutoEntity produtoId(Integer produtoId) {
        this.setProdutoId(produtoId);
        return this;
    }

    public void setValorUnitarioProdutoMomentoVenda(BigDecimal valor) {
        if (valor != null) {
            this.valorUnitarioProdutoMomentoVenda = valor.setScale(2, RoundingMode.HALF_UP);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PedidoProdutoEntity)) {
            return false;
        }
        return id != null && id.equals(((PedidoProdutoEntity) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "PedidoProduto{" +
                "id=" + getId() +
                ", quantidade=" + getQuantidade() +
                "}";
    }
}
