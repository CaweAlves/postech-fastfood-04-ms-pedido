package org.fiap.fastfoodpedidos.application.port.driver;

import org.fiap.fastfoodpedidos.domain.model.Pedido;

public interface IniciarPedidoUseCase {
    Pedido iniciarPedido(Pedido pedidoRecebido);
}
