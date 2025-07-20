package org.fiap.fastfoodpedidos.application.usecase;

import org.fiap.fastfoodpedidos.application.port.driven.ConsultarPedidos;
import org.fiap.fastfoodpedidos.domain.model.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarPedidosUseCaseImpTest {

    @Mock
    private ConsultarPedidos consultarPedidos;

    @InjectMocks
    private BuscarPedidosUseCaseImp buscarPedidosUseCase;

    @Test
    void deveRetornarListaDePedidos_QuandoPedidosExistem() {
        Pedido pedido1 = new Pedido();
        pedido1.setId(1);
        List<Pedido> listaDePedidosEsperada = List.of(pedido1);

        when(consultarPedidos.execute()).thenReturn(listaDePedidosEsperada);

        List<Pedido> resultado = buscarPedidosUseCase.execute();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.getFirst().getId());

        verify(consultarPedidos, times(1)).execute();
    }

    @Test
    void deveRetornarListaVazia_QuandoNaoExistemPedidos() {
        when(consultarPedidos.execute()).thenReturn(Collections.emptyList());

        List<Pedido> resultado = buscarPedidosUseCase.execute();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(consultarPedidos, times(1)).execute();
    }
}