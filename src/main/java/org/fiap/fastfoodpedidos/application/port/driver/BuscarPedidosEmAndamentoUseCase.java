package org.fiap.fastfoodpedidos.application.port.driver;

import org.fiap.fastfoodpedidos.domain.model.Pedido;

import java.util.List;

public interface BuscarPedidosEmAndamentoUseCase {

    List<Pedido> execute();

}
