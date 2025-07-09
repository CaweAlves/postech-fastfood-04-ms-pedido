package org.fiap.fastfoodpedidos.infrastructure.adapter.rest.pedido.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProdutoQuantidadePedidoRequest(


        @NotNull(message = "Produtos devem ser informados")
        Integer produto,
        @Positive
        @NotNull(message = "A quantidade deve ser informada")
        Integer quantidade) {
}
