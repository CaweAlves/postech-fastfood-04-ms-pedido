package org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.fiap.fastfoodpedidos.domain.enumeration.PedidoStatus;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pedido")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PedidoEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Setter
    @Getter
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PedidoStatus status;

    @Setter
    @Getter
    @NotNull
    @Column(name = "total", precision = 21, scale = 2, nullable = false)
    private BigDecimal total;

    @Getter
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PedidoProdutoEntity> pedidoProdutos = new HashSet<>();

    @Setter
    @Getter
    @ManyToOne
    private ClienteEntity cliente;

    @Getter
    @Setter
    @Column(name = "data_hora_pedido_recebido")
    private LocalDateTime dataHoraPedidoRecebido;

    @ManyToMany
    @JoinTable(
            name = "pedido_produto",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id")
    )

    public Integer getId() {
        return this.id;
    }

    public PedidoEntity id(Integer id) {
        this.setId(id);
        return this;
    }

    public PedidoEntity status(PedidoStatus status) {
        this.setStatus(status);
        return this;
    }

    public PedidoEntity total(BigDecimal total) {
        this.setTotal(total);
        return this;
    }

    public void setPedidoProdutos(Set<PedidoProdutoEntity> pedidoProdutoEntities) {
        if (this.pedidoProdutos != null) {
            this.pedidoProdutos.forEach(i -> i.setPedido(null));
        }
        if (pedidoProdutoEntities != null) {
            pedidoProdutoEntities.forEach(i -> i.setPedido(this));
        }
        this.pedidoProdutos = pedidoProdutoEntities;
    }

    public PedidoEntity pedidoProdutos(Set<PedidoProdutoEntity> pedidoProdutoEntities) {
        this.setPedidoProdutos(pedidoProdutoEntities);
        return this;
    }

    public void addPedidoProduto(PedidoProdutoEntity pedidoProdutoEntity) {
        this.pedidoProdutos.add(pedidoProdutoEntity);
        pedidoProdutoEntity.setPedido(this);
    }

    public void removePedidoProduto(PedidoProdutoEntity pedidoProdutoEntity) {
        this.pedidoProdutos.remove(pedidoProdutoEntity);
        pedidoProdutoEntity.setPedido(null);
    }

    public PedidoEntity cliente(ClienteEntity clienteEntity) {
        this.setCliente(clienteEntity);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PedidoEntity)) {
            return false;
        }
        return id != null && id.equals(((PedidoEntity) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + getId() +
                ", status='" + getStatus() + "'" +
                ", total=" + getTotal() +
                "}";
    }

}
