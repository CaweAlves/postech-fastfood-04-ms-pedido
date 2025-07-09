package org.fiap.fastfoodpedidos.application.usecase;

import lombok.AllArgsConstructor;
import org.fiap.fastfoodpedidos.application.port.driver.RemoverProdutoPedidoUseCase;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.PedidoEntity;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.PedidoProdutoEntity;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.repository.PedidoRepository;

import java.util.Optional;

@AllArgsConstructor
public class RemoverProdutoPedidoUseCaseImpl implements RemoverProdutoPedidoUseCase {

    private final PedidoRepository pedidoRepository;

    @Override
    public void removerProduto(Integer pedidoId, PedidoProdutoEntity produto) {
        Optional<PedidoEntity> pedido = this.pedidoRepository.findById(pedidoId);
        pedido.ifPresent(pedidoEntity -> pedidoEntity.removePedidoProduto(produto));
    }

}
