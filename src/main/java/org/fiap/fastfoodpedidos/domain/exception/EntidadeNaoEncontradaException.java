package org.fiap.fastfoodpedidos.domain.exception;

import org.fiap.fastfoodpedidos.infrastructure.adapter.rest.exception.BadRequestAlertException;

public class EntidadeNaoEncontradaException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public EntidadeNaoEncontradaException(String entity) {
        super(entity + " não encontrado(a).", entity, "entity-not-found");
    }
}
