package org.fiap.fastfoodpedidos.domain.exception;

import org.fiap.fastfoodpedidos.domain.enumeration.PedidoStatus;
import org.fiap.fastfoodpedidos.infrastructure.adapter.rest.exception.BadRequestAlertException;

public class ImpossivelTransicionarStatusPedidoException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public ImpossivelTransicionarStatusPedidoException(PedidoStatus origem, PedidoStatus destino) {
        super(
                String.format("Não é possível fazer a transição do status %s para %s.", origem, destino),
                "pedido",
                "impossivelTransicionarStatusPedido"
        );
    }
}
