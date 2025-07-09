package org.fiap.fastfoodpedidos.infrastructure.adapter.rest.pedido.dto;

import org.fiap.fastfoodpedidos.domain.enumeration.PedidoStatus;
import org.fiap.fastfoodpedidos.domain.model.Cliente;
import org.fiap.fastfoodpedidos.domain.model.PedidoProduto;

import java.math.BigDecimal;
import java.util.Set;

public record DetalhePedidoResponse(
        Integer id,
        PedidoStatus status,
        BigDecimal total,
        Set<PedidoProduto> pedidoProdutos,
        Cliente cliente) {
}