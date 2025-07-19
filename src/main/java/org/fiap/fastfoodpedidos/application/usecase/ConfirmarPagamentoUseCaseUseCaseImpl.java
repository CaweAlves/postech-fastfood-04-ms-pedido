package org.fiap.fastfoodpedidos.application.usecase;

import lombok.RequiredArgsConstructor;
import org.fiap.fastfoodpedidos.application.port.driver.AtualizarStatusPedidoUseCase;
import org.fiap.fastfoodpedidos.application.port.driver.ConfirmarPagamentoUseCase;
import org.fiap.fastfoodpedidos.domain.enumeration.PedidoStatus;

@RequiredArgsConstructor
public class ConfirmarPagamentoUseCaseUseCaseImpl implements ConfirmarPagamentoUseCase {

    private final AtualizarStatusPedidoUseCase atualizarStatusPedidoUseCase;

    @Override
    public void execute(Integer pedidoId) {
        if (pedidoId == null) {
            throw new IllegalArgumentException("ID do pedido inválido para confirmação.");
        }
        atualizarStatusPedidoUseCase.execute(pedidoId, PedidoStatus.RECEBIDO);
    }
}