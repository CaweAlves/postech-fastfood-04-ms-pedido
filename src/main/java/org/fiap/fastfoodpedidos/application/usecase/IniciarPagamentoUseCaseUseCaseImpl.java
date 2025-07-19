package org.fiap.fastfoodpedidos.application.usecase;

import lombok.AllArgsConstructor;
import org.fiap.fastfoodpedidos.application.port.driven.ConsultarPedido;
import org.fiap.fastfoodpedidos.application.port.driven.SolicitarPagamento;
import org.fiap.fastfoodpedidos.application.port.driver.IniciarPagamentoUseCase;
import org.fiap.fastfoodpedidos.domain.enumeration.PedidoStatus;
import org.fiap.fastfoodpedidos.domain.exception.PedidoNaoAplicavelParaPagamentoException;
import org.fiap.fastfoodpedidos.domain.model.Pedido;
import org.springframework.core.io.Resource; // Importar Resource

@AllArgsConstructor
public class IniciarPagamentoUseCaseUseCaseImpl implements IniciarPagamentoUseCase {

    private final SolicitarPagamento solicitarPagamento;
    private final ConsultarPedido consultarPedido;

    @Override
    public Resource execute(Integer idPedido) {
        Pedido pedido = this.consultarPedido.execute(idPedido);

        if (!pedido.getStatus().equals(PedidoStatus.AGUARDANDO_PAGAMENTO)) {
            throw new PedidoNaoAplicavelParaPagamentoException();
        }

        return this.solicitarPagamento.gerarQRCode(pedido);
    }
}