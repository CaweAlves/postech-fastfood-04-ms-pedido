package org.fiap.fastfoodpedidos.infrastructure.adapter.rest.pedido.dto;

import jakarta.validation.constraints.NotNull;
import org.fiap.fastfoodpedidos.domain.enumeration.PedidoStatus;

public record AtualizarStatusRequest(
        @NotNull(message = "O novo Status para o pedido deve ser informado")
        PedidoStatus status
) {
}
