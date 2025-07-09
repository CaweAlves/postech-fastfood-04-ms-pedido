package org.fiap.fastfoodpedidos.domain.exception;

import org.fiap.fastfoodpedidos.infrastructure.adapter.rest.exception.BadRequestAlertException;

public class ImpossivelCancelarPagamentoException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public ImpossivelCancelarPagamentoException() {
        super("Este pagamento não é elegível para cancelamento.", "pagamento", "impossivelCancelarPagamento");
    }
}
