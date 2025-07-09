package org.fiap.fastfoodpedidos.domain.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoProduto {
    private Integer id;
    @Valid
    private Pedido pedido;
    @Valid
    private Produto produto;
    @Positive
    @NotNull(message = "o valor unitário no momento da venda deve ser informado")
    private BigDecimal valorUnitarioProdutoMomentoVenda;
    @Positive
    @NotNull(message = "A quantidade deve ser informada")
    private Integer quantidade;
}
