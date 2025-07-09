package org.fiap.fastfoodpedidos.application.usecase;

import lombok.RequiredArgsConstructor;
import org.fiap.fastfoodpedidos.application.port.driven.ConsultarPedido;
import org.fiap.fastfoodpedidos.application.port.driven.SalvarPedido;
import org.fiap.fastfoodpedidos.application.port.driver.AtualizarStatusPedidoUseCase;
import org.fiap.fastfoodpedidos.domain.enumeration.PedidoStatus;
import org.fiap.fastfoodpedidos.domain.exception.ImpossivelTransicionarStatusPedidoException;
import org.fiap.fastfoodpedidos.domain.model.Pedido;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class AtualizarStatusPedidoUseCaseImp implements AtualizarStatusPedidoUseCase {

    private final ConsultarPedido consultarPedido;
    private final SalvarPedido salvarPedido;

    @Override
    public Pedido execute(Integer idPedido, PedidoStatus novoStatus) {
        Pedido pedido = consultarPedido.execute(idPedido);
        PedidoStatus statusAtual = pedido.getStatus();

        if (!isPossivelTransicionarStatus(statusAtual, novoStatus)) {
            throw new ImpossivelTransicionarStatusPedidoException(statusAtual, novoStatus);
        }

        pedido.setStatus(novoStatus);
        pedido.setDataHoraPedidoRecebido(LocalDateTime.now());
        return salvarPedido.execute(pedido);

    }

    private boolean isPossivelTransicionarStatus(PedidoStatus status, PedidoStatus novoStatus) {
        return status.podeTransicionarPara(novoStatus);
    }
}