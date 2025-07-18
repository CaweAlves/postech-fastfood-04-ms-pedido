package org.fiap.fastfoodpedidos.application.usecase;

import lombok.AllArgsConstructor;
import org.fiap.fastfoodpedidos.application.port.driven.ManipularPagamento;
import org.fiap.fastfoodpedidos.application.port.driver.AtualizarStatusPedidoUseCase;
import org.fiap.fastfoodpedidos.application.port.driver.CancelarPagamentoUseCase;
import org.fiap.fastfoodpedidos.domain.enumeration.PagamentoStatus;
import org.fiap.fastfoodpedidos.domain.enumeration.PedidoStatus;
import org.fiap.fastfoodpedidos.domain.exception.ImpossivelCancelarPagamentoException;
import org.fiap.fastfoodpedidos.domain.model.Pagamento;

@AllArgsConstructor
public class CancelarPagamentoUseCaseUseCaseImpl implements CancelarPagamentoUseCase {

    private final ManipularPagamento manipularPagamento;
    private final AtualizarStatusPedidoUseCase atualizarStatusPedidoUseCase;

    @Override
    public Pagamento execute(String idExternoPagamento) {
        Pagamento pagamento = manipularPagamento.buscarPagamentoPorIdExterno(idExternoPagamento);

        if (!isPossivelCancelarPagamento(pagamento)) {
            throw new ImpossivelCancelarPagamentoException();
        }

        pagamento.setStatus(PagamentoStatus.CANCELADO);
        atualizarStatusPedidoUseCase.execute(pagamento.getPedido().getId(), PedidoStatus.CANCELADO);
        return manipularPagamento.alterarPagamento(pagamento);
    }

    private boolean isPossivelCancelarPagamento(Pagamento pagamento) {
        boolean isPagamentoPendente = PagamentoStatus.PENDENTE.equals(pagamento.getStatus());
        boolean isPedidoAguardandoPagamento = PedidoStatus.AGUARDANDO_PAGAMENTO.equals(pagamento.getPedido().getStatus());
        return isPagamentoPendente && isPedidoAguardandoPagamento;
    }
}
