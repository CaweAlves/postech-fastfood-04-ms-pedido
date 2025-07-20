package org.fiap.fastfoodpedidos.domain.exception;

import org.junit.jupiter.api.Test;
import org.zalando.problem.Status;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Teste unitário para a classe ProdutoUtilizadoEmPedidoException.
 */
class ProdutoUtilizadoEmPedidoExceptionTest {

    @Test
    void deveCriarExcecaoComValoresCorretos() {
        ProdutoUtilizadoEmPedidoException exception = new ProdutoUtilizadoEmPedidoException();

        assertThat(exception.getEntityName()).isEqualTo("Produto");
        assertThat(exception.getErrorKey()).isEqualTo("produtoUtilizadoEmPedido");
        assertThat(exception.getTitle()).isEqualTo("Não é possível excluir o produto pois ele está associado à um pedido.");
        assertThat(exception.getStatus()).isEqualTo(Status.BAD_REQUEST);
    }
}