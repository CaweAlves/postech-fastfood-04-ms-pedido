package org.fiap.fastfoodpedidos.application.usecase;

import org.fiap.fastfoodpedidos.application.port.driven.ConsultarPedido;
import org.fiap.fastfoodpedidos.domain.model.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class BuscarPedidoUseCaseImpTest {

    @Mock
    private ConsultarPedido consultarPedido;

    @InjectMocks
    private BuscarPedidoUseCaseImp buscarPedidoUseCase;

    @Test
    void deveRetornarPedido_QuandoEncontradoPeloId() {
        int pedidoId = 1;
        Pedido pedidoEsperado = new Pedido();
        pedidoEsperado.setId(pedidoId);

        when(consultarPedido.execute(pedidoId)).thenReturn(pedidoEsperado);

        Pedido pedidoEncontrado = buscarPedidoUseCase.execute(pedidoId);

        assertNotNull(pedidoEncontrado);
        assertEquals(pedidoId, pedidoEncontrado.getId());

        verify(consultarPedido, times(1)).execute(pedidoId);
    }

    @Test
    void devePropagarExcecao_QuandoPedidoNaoEhEncontrado() {
        int pedidoIdInexistente = 99;
        ResponseStatusException excecaoEsperada = new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado.");

        when(consultarPedido.execute(pedidoIdInexistente)).thenThrow(excecaoEsperada);

        ResponseStatusException excecaoLancada = assertThrows(ResponseStatusException.class, () -> {
            buscarPedidoUseCase.execute(pedidoIdInexistente);
        });

        assertEquals(HttpStatus.NOT_FOUND, excecaoLancada.getStatusCode());
        assertEquals("Pedido não encontrado.", excecaoLancada.getReason());
    }
}