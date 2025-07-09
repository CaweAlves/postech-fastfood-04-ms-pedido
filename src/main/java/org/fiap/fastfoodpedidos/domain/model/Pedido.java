package org.fiap.fastfoodpedidos.domain.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.fiap.fastfoodpedidos.domain.enumeration.PedidoStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Pedido {

    private Integer id;
    @NotNull
    private PedidoStatus status;
    @Positive
    @NotNull(message = "O total deve ser informado")
    private BigDecimal total;
    @Valid
    private Set<PedidoProduto> pedidoProdutos;
    @Valid
    @Null
    private Cliente cliente;
    private LocalDateTime dataHoraPedidoRecebido;

}
