package org.fiap.fastfoodpedidos.application.usecase;

import lombok.RequiredArgsConstructor;
import org.fiap.fastfoodpedidos.application.port.driver.AtualizarStatusPedidoUseCase;
import org.fiap.fastfoodpedidos.application.port.driver.CancelarPagamentoUseCase;
import org.fiap.fastfoodpedidos.domain.enumeration.PedidoStatus;
import org.fiap.fastfoodpedidos.domain.model.Pagamento;

@RequiredArgsConstructor
public class CancelarPagamentoUseCaseUseCaseImpl implements CancelarPagamentoUseCase {

    private final AtualizarStatusPedidoUseCase atualizarStatusPedidoUseCase;

    @Override
    public void execute(Integer pedidoId) {
        if (pedidoId == null) {
            throw new IllegalArgumentException("Dados do pagamento ou pedido inválidos para cancelamento.");
        }

        atualizarStatusPedidoUseCase.execute(pedidoId, PedidoStatus.CANCELADO);
    }
}