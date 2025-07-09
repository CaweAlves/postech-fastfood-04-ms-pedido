package org.fiap.fastfoodpedidos.application.port.driven;

import org.fiap.fastfoodpedidos.domain.model.Pedido;

import java.util.List;

public interface ConsultarPedidosEmAndamento {

    List<Pedido> consultarPedidosEmAndamento();
}
