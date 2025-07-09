package org.fiap.fastfoodpedidos.application.port.driver;

import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.PedidoProdutoEntity;

public interface AdicionarProdutoAoPedidoUseCase {
    void adicionarProduto(Integer pedidoId, PedidoProdutoEntity produto);
}
