package org.fiap.fastfoodpedidos.infrastructure.adapter.messaging;

import org.fiap.fastfoodpedidos.domain.enumeration.PagamentoStatus;

public record ResultadoPagamentoDTO(
        Integer pedidoId,
        PagamentoStatus status
) {}