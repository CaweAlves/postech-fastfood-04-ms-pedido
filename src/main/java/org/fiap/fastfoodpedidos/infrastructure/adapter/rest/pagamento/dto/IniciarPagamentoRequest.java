package org.fiap.fastfoodpedidos.infrastructure.adapter.rest.pagamento.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IniciarPagamentoRequest {
    @NotNull(message = "O id do pedido é obrigatório")
    private Integer pedidoId;
}