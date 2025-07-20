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
class ConfirmarPagamentoUseCaseUseCaseImplTest {

    @Mock
    private AtualizarStatusPedidoUseCase atualizarStatusPedidoUseCase;

    @InjectMocks
    private ConfirmarPagamentoUseCaseUseCaseImpl confirmarPagamentoUseCase;

    @Test
    void deveChamarAtualizarStatusParaRecebido_QuandoExecutado() {
        int pedidoId = 1;
        confirmarPagamentoUseCase.execute(pedidoId);

        verify(atualizarStatusPedidoUseCase, times(1)).execute(pedidoId, PedidoStatus.RECEBIDO);
    }

    @Test
    void deveLancarExcecao_QuandoPedidoIdEhNulo() {
        Integer pedidoIdNulo = null;

        assertThrows(IllegalArgumentException.class, () -> {
            confirmarPagamentoUseCase.execute(pedidoIdNulo);
        });

        verify(atualizarStatusPedidoUseCase, never()).execute(anyInt(), any(PedidoStatus.class));
    }
}