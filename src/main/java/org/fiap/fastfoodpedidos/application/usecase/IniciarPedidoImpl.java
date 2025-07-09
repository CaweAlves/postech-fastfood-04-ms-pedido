package org.fiap.fastfoodpedidos.application.usecase;

import org.fiap.fastfoodpedidos.application.port.driver.IniciarPedidoUseCase;
import org.fiap.fastfoodpedidos.domain.model.Pedido;

public class IniciarPedidoImpl implements IniciarPedidoUseCase {

    @Override
    public Pedido iniciarPedido(Pedido pedidoRecebido) {
        return pedidoRecebido;
    }

}
