package org.fiap.fastfoodpedidos.domain.exception;

import org.fiap.fastfoodpedidos.infrastructure.adapter.rest.exception.BadRequestAlertException;

public class ImpossivelConfirmarPagamentoException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public ImpossivelConfirmarPagamentoException() {
        super("Este pagamento não é elegível para confirmação.", "pagamento", "impossivelConfirmarPagamento");
    }
}
