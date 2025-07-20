package org.fiap.fastfoodpedidos.domain.exception;

import org.junit.jupiter.api.Test;
import org.zalando.problem.Status;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Teste unitário para a classe EntidadeNaoEncontradaException.
 */
class EntidadeNaoEncontradaExceptionTest {

    @Test
    void deveCriarExcecaoComMensagemEEntidadeCorretas() {
        String nomeDaEntidade = "Pedido";

        EntidadeNaoEncontradaException exception = new EntidadeNaoEncontradaException(nomeDaEntidade);

        assertThat(exception.getEntityName()).isEqualTo(nomeDaEntidade);
        assertThat(exception.getErrorKey()).isEqualTo("entity-not-found");
        assertThat(exception.getTitle()).isEqualTo("Pedido não encontrado(a).");
        assertThat(exception.getStatus()).isEqualTo(Status.BAD_REQUEST);
    }
}