package org.fiap.fastfoodpedidos.application.port.driven;

import org.fiap.fastfoodpedidos.domain.model.Produto;

import java.util.List;

public interface BuscarProdutos {
    List<Produto> buscarTodos();
}
