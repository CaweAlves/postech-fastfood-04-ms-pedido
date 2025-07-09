package org.fiap.fastfoodpedidos.application.port.driven;

import org.fiap.fastfoodpedidos.domain.model.Pedido;
import org.fiap.fastfoodpedidos.domain.model.Produto;

import java.util.List;

public interface BuscarPedidosPorUmProduto {

    List<Pedido> execute(Produto produto);

}
