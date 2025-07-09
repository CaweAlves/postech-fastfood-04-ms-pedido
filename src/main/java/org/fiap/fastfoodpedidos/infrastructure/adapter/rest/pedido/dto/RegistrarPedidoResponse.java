package org.fiap.fastfoodpedidos.infrastructure.adapter.rest.pedido.dto;

import org.fiap.fastfoodpedidos.domain.enumeration.PedidoStatus;

import java.math.BigDecimal;

public record RegistrarPedidoResponse(
        Integer id,
        PedidoStatus status,
        BigDecimal total) {
}