package org.fiap.fastfoodpedidos.infrastructure.adapter.rest.pedido.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record RegistrarPedidoRequest(
        Integer cliente,
        @NotNull(message = "Produtos devem ser informados")
        @Valid
        Set<ProdutoQuantidadePedidoRequest> produtos) {
}



