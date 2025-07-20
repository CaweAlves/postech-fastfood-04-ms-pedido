package org.fiap.fastfoodpedidos.application.usecase;

import org.fiap.fastfoodpedidos.application.port.driven.ConsultarPedido;
import org.fiap.fastfoodpedidos.application.port.driven.SalvarPedido;
import org.fiap.fastfoodpedidos.domain.enumeration.PedidoStatus;
import org.fiap.fastfoodpedidos.domain.exception.ImpossivelTransicionarStatusPedidoException;
import org.fiap.fastfoodpedidos.domain.model.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarStatusPedidoUseCaseImpTest {

    @Mock
    private ConsultarPedido consultarPedido;

    @Mock
    private SalvarPedido salvarPedido;

    @InjectMocks
    private AtualizarStatusPedidoUseCaseImp atualizarStatusPedidoUseCase;

    @Test
    void deveAtualizarStatusDoPedido_QuandoTransicaoEhValida() {
        Pedido pedidoExistente = new Pedido();
        pedidoExistente.setId(1);
        pedidoExistente.setStatus(PedidoStatus.RECEBIDO);

        when(consultarPedido.execute(1)).thenReturn(pedidoExistente);
        when(salvarPedido.execute(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pedido pedidoAtualizado = atualizarStatusPedidoUseCase.execute(1, PedidoStatus.EM_PREPARACAO);

        assertNotNull(pedidoAtualizado);
        assertEquals(PedidoStatus.EM_PREPARACAO, pedidoAtualizado.getStatus());

        verify(consultarPedido, times(1)).execute(1);
        verify(salvarPedido, times(1)).execute(pedidoExistente);
    }

    @Test
    void deveLancarExcecao_QuandoTransicaoDeStatusEhInvalida() {
        Pedido pedidoExistente = new Pedido();
        pedidoExistente.setId(2);
        pedidoExistente.setStatus(PedidoStatus.PRONTO);

        when(consultarPedido.execute(2)).thenReturn(pedidoExistente);

        assertThrows(ImpossivelTransicionarStatusPedidoException.class, () -> {
            atualizarStatusPedidoUseCase.execute(2, PedidoStatus.EM_PREPARACAO);
        });

        verify(salvarPedido, never()).execute(any(Pedido.class));
    }
}