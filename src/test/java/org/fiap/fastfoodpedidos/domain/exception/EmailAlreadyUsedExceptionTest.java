package org.fiap.fastfoodpedidos.domain.exception;

import org.junit.jupiter.api.Test;
import org.zalando.problem.Status;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Teste unitário para a classe EmailAlreadyUsedException.
 */
class EmailAlreadyUsedExceptionTest {

    @Test
    void deveCriarExcecaoComValoresCorretos() {
        EmailAlreadyUsedException exception = new EmailAlreadyUsedException();

        assertThat(exception.getEntityName()).isEqualTo("userManagement");
        assertThat(exception.getErrorKey()).isEqualTo("emailexists");
        assertThat(exception.getTitle()).isEqualTo("Email is already in use!");
        assertThat(exception.getStatus()).isEqualTo(Status.BAD_REQUEST);
    }
}