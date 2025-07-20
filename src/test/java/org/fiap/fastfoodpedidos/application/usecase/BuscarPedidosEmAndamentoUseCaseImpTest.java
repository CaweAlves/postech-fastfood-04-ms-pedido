package org.fiap.fastfoodpedidos.application.usecase;

import org.fiap.fastfoodpedidos.application.port.driven.ConsultarPedidosEmAndamento;
import org.fiap.fastfoodpedidos.domain.enumeration.PedidoStatus;
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
class BuscarPedidosEmAndamentoUseCaseImpTest {

    @Mock
    private ConsultarPedidosEmAndamento consultarPedidosEmAndamento;

    @InjectMocks
    private BuscarPedidosEmAndamentoUseCaseImp buscarPedidosEmAndamentoUseCase;

    @Test
    void deveRetornarListaDePedidos_QuandoExistemPedidosEmAndamento() {
        Pedido pedidoEmAndamento = new Pedido();
        pedidoEmAndamento.setId(1);
        pedidoEmAndamento.setStatus(PedidoStatus.EM_PREPARACAO);
        List<Pedido> listaDePedidosEsperada = List.of(pedidoEmAndamento);

        when(consultarPedidosEmAndamento.consultarPedidosEmAndamento()).thenReturn(listaDePedidosEsperada);

        List<Pedido> resultado = buscarPedidosEmAndamentoUseCase.execute();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getId());

        verify(consultarPedidosEmAndamento, times(1)).consultarPedidosEmAndamento();
    }

    @Test
    void deveRetornarListaVazia_QuandoNaoExistemPedidosEmAndamento() {
        when(consultarPedidosEmAndamento.consultarPedidosEmAndamento()).thenReturn(Collections.emptyList());

        List<Pedido> resultado = buscarPedidosEmAndamentoUseCase.execute();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(consultarPedidosEmAndamento, times(1)).consultarPedidosEmAndamento();
    }
}