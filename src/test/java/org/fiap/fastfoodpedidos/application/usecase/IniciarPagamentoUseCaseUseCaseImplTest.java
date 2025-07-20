package org.fiap.fastfoodpedidos.application.usecase;

import org.fiap.fastfoodpedidos.application.port.driven.ConsultarPedido;
import org.fiap.fastfoodpedidos.application.port.driven.SolicitarPagamento;
import org.fiap.fastfoodpedidos.domain.enumeration.PedidoStatus;
import org.fiap.fastfoodpedidos.domain.exception.PedidoNaoAplicavelParaPagamentoException;
import org.fiap.fastfoodpedidos.domain.model.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IniciarPagamentoUseCaseUseCaseImplTest {

    @Mock
    private SolicitarPagamento solicitarPagamento;

    @Mock
    private ConsultarPedido consultarPedido;

    @InjectMocks
    private IniciarPagamentoUseCaseUseCaseImpl iniciarPagamentoUseCase;

    @Test
    void deveIniciarPagamentoComSucesso_QuandoPedidoEstaAguardandoPagamento() {
        int pedidoId = 1;
        Pedido pedidoAguardandoPagamento = new Pedido();
        pedidoAguardandoPagamento.setId(pedidoId);
        pedidoAguardandoPagamento.setStatus(PedidoStatus.AGUARDANDO_PAGAMENTO);

        Resource qrCodeResource = new ByteArrayResource("qr-code-fake".getBytes());

        when(consultarPedido.execute(pedidoId)).thenReturn(pedidoAguardandoPagamento);
        when(solicitarPagamento.gerarQRCode(pedidoAguardandoPagamento)).thenReturn(qrCodeResource);

        Resource resultado = iniciarPagamentoUseCase.execute(pedidoId);

        assertNotNull(resultado);
        assertEquals(qrCodeResource, resultado);

        verify(consultarPedido, times(1)).execute(pedidoId);
        verify(solicitarPagamento, times(1)).gerarQRCode(pedidoAguardandoPagamento);
    }

    @Test
    void deveLancarExcecao_QuandoStatusDoPedidoNaoEhAguardandoPagamento() {
        int pedidoId = 2;
        Pedido pedidoJaPago = new Pedido();
        pedidoJaPago.setId(pedidoId);
        pedidoJaPago.setStatus(PedidoStatus.RECEBIDO); // Status inválido para iniciar pagamento

        when(consultarPedido.execute(pedidoId)).thenReturn(pedidoJaPago);

        assertThrows(PedidoNaoAplicavelParaPagamentoException.class, () -> {
            iniciarPagamentoUseCase.execute(pedidoId);
        });

        verify(solicitarPagamento, never()).gerarQRCode(any(Pedido.class));
    }
}