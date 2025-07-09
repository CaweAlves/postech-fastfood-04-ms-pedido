package org.fiap.fastfoodpedidos.domain.exception;

import org.fiap.fastfoodpedidos.infrastructure.adapter.rest.exception.BadRequestAlertException;

public class PedidoNaoAplicavelParaPagamentoException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public PedidoNaoAplicavelParaPagamentoException() {
        super("Esse pedido não é aplicável para pagamento.", "Pedido", "pedidoNaoAplicavelParaPagamento");
    }
}
