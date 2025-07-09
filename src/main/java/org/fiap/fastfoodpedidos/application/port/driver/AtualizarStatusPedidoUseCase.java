package org.fiap.fastfoodpedidos.application.port.driver;

import org.fiap.fastfoodpedidos.domain.enumeration.PedidoStatus;
import org.fiap.fastfoodpedidos.domain.model.Pedido;

public interface AtualizarStatusPedidoUseCase {

    Pedido execute(Integer idPedido, PedidoStatus novoStatus);

}
