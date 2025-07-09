package org.fiap.fastfoodpedidos.application.port.driver;

import org.fiap.fastfoodpedidos.domain.model.Pedido;

public interface CriarPedidoUseCase {

    Pedido execute(Pedido pedido);
}
