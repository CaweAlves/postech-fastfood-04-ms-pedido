package org.fiap.fastfoodpedidos.domain.exception;

import org.junit.jupiter.api.Test;
import org.zalando.problem.Status;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Teste unitário para a classe ImpossivelCancelarPagamentoException.
 */
class ImpossivelCancelarPagamentoExceptionTest {

    @Test
    void deveCriarExcecaoComValoresCorretos() {
        ImpossivelCancelarPagamentoException exception = new ImpossivelCancelarPagamentoException();

        assertThat(exception.getEntityName()).isEqualTo("pagamento");
        assertThat(exception.getErrorKey()).isEqualTo("impossivelCancelarPagamento");
        assertThat(exception.getTitle()).isEqualTo("Este pagamento não é elegível para cancelamento.");
        assertThat(exception.getStatus()).isEqualTo(Status.BAD_REQUEST);
    }
}