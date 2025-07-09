package org.fiap.fastfoodpedidos.application.port.driven;

import org.fiap.fastfoodpedidos.domain.model.Pedido;

public interface SalvarPedido {

    Pedido execute(Pedido pedido);

}
