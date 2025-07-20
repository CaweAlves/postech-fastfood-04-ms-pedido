package org.fiap.fastfoodpedidos.infrastructure.adapter.rest.pedido.dto;

import org.fiap.fastfoodpedidos.domain.enumeration.PedidoStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Teste unitário para o DTO AtualizarStatusRequest.
 * Foco: Validar a criação e o acesso aos dados do record.
 */
class AtualizarStatusRequestTest {

    @Test
    void deveCriarRequest_eRetornarStatusCorretamente() {
        PedidoStatus statusEsperado = PedidoStatus.RECEBIDO;

        AtualizarStatusRequest request = new AtualizarStatusRequest(statusEsperado);

        assertThat(request).isNotNull();
        assertThat(request.status()).isEqualTo(statusEsperado);
    }
}