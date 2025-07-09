package org.fiap.fastfoodpedidos.application.port.driver;


import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.PedidoProdutoEntity;

public interface RemoverProdutoPedidoUseCase {
    void removerProduto(Integer pedidoId, PedidoProdutoEntity produto);
}
