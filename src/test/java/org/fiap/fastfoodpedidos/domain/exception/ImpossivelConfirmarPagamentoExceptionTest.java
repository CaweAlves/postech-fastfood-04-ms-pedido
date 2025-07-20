package org.fiap.fastfoodpedidos.domain.exception;

import org.junit.jupiter.api.Test;
import org.zalando.problem.Status;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Teste unitário para a classe ImpossivelConfirmarPagamentoException.
 */
class ImpossivelConfirmarPagamentoExceptionTest {

    @Test
    void deveCriarExcecaoComValoresCorretos() {
        ImpossivelConfirmarPagamentoException exception = new ImpossivelConfirmarPagamentoException();

        assertThat(exception.getEntityName()).isEqualTo("pagamento");
        assertThat(exception.getErrorKey()).isEqualTo("impossivelConfirmarPagamento");
        assertThat(exception.getTitle()).isEqualTo("Este pagamento não é elegível para confirmação.");
        assertThat(exception.getStatus()).isEqualTo(Status.BAD_REQUEST);
    }
}