package org.fiap.fastfoodpedidos.domain.exception;

import org.fiap.fastfoodpedidos.infrastructure.adapter.rest.exception.BadRequestAlertException;

public class ProdutoUtilizadoEmPedidoException extends BadRequestAlertException {
    public ProdutoUtilizadoEmPedidoException() {
        super("Não é possível excluir o produto pois ele está associado à um pedido.", "Produto", "produtoUtilizadoEmPedido");
    }
}
