package org.fiap.fastfoodpedidos.application.usecase;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.fiap.fastfoodpedidos.application.port.driver.AtualizarStatusPedidoUseCase;
import org.fiap.fastfoodpedidos.application.port.driver.ConfirmarPagamentoUseCase;
import org.fiap.fastfoodpedidos.domain.enumeration.PagamentoStatus;
import org.fiap.fastfoodpedidos.domain.enumeration.PedidoStatus;
import org.fiap.fastfoodpedidos.domain.exception.ImpossivelConfirmarPagamentoException;
import org.fiap.fastfoodpedidos.domain.model.Pagamento;

@AllArgsConstructor
public class ConfirmarPagamentoUseCaseUseCaseImpl implements ConfirmarPagamentoUseCase {

    private final AtualizarStatusPedidoUseCase atualizarStatusPedidoUseCase;

    @Override
    @Transactional
    public Pagamento execute(String idExternoPagamento) {
        Pagamento pagamento = new Pagamento();
        return pagamento;
    }

    private boolean isPossivelConfirmarPagamento(Pagamento pagamento) {
        boolean isPagamentoPendente = PagamentoStatus.PENDENTE.equals(pagamento.getStatus());
        boolean isPedidoAguardandoPagamento = PedidoStatus.AGUARDANDO_PAGAMENTO.equals(pagamento.getPedido().getStatus());
        return isPagamentoPendente && isPedidoAguardandoPagamento;
    }
}
