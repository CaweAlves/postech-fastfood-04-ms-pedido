package org.fiap.fastfoodpedidos.application.usecase;

import org.fiap.fastfoodpedidos.application.port.driver.AtualizarStatusPedidoUseCase;
import org.fiap.fastfoodpedidos.domain.enumeration.PedidoStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelarPagamentoUseCaseUseCaseImplTest {

    @Mock
    private AtualizarStatusPedidoUseCase atualizarStatusPedidoUseCase;

    @InjectMocks
    private CancelarPagamentoUseCaseUseCaseImpl cancelarPagamentoUseCase;

    @Test
    void deveChamarAtualizarStatusParaCancelado_QuandoExecutado() {
        int pedidoId = 1;

        cancelarPagamentoUseCase.execute(pedidoId);

        verify(atualizarStatusPedidoUseCase, times(1)).execute(pedidoId, PedidoStatus.CANCELADO);
    }

    @Test
    void deveLancarExcecao_QuandoPedidoIdEhNulo() {
        Integer pedidoIdNulo = null;

        assertThrows(IllegalArgumentException.class, () -> {
            cancelarPagamentoUseCase.execute(pedidoIdNulo);
        });

        verify(atualizarStatusPedidoUseCase, never()).execute(anyInt(), any(PedidoStatus.class));
    }
}