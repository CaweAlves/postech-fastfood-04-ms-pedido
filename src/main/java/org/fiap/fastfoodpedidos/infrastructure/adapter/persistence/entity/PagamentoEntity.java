package org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.fiap.fastfoodpedidos.domain.enumeration.PagamentoStatus;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "pagamento")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_externo", nullable = false, unique = true)
    private String idExterno;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pedido_id", nullable = false)
    private PedidoEntity pedido;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PagamentoStatus status;

    @Column(name = "qr_code", columnDefinition = "TEXT")
    private String qrCode;
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PagamentoEntity)) {
            return false;
        }
        return id != null && id.equals(((PagamentoEntity) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "PagamentoEntity{" +
                "id=" + id +
                ", idExterno='" + idExterno + '\'' +
                ", pedido=" + pedido +
                ", status=" + status +
                ", qrCode='" + qrCode + '\'' +
                '}';
    }
}