package org.fiap.fastfoodpedidos.application.usecase;

import lombok.AllArgsConstructor;
import org.fiap.fastfoodpedidos.application.port.driver.AdicionarProdutoAoPedidoUseCase;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.PedidoEntity;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.PedidoProdutoEntity;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.repository.PedidoRepository;

import java.util.Optional;

@AllArgsConstructor
public class AdicionarProdutoAoPedidoUseCaseImpl implements AdicionarProdutoAoPedidoUseCase {

    private final PedidoRepository pedidoRepository;

    @Override
    public void adicionarProduto(Integer pedidoId, PedidoProdutoEntity produto) {
        Optional<PedidoEntity> pedido = this.pedidoRepository.findById(pedidoId);
        pedido.ifPresent(pedidoEntity -> pedidoEntity.addPedidoProduto(produto));

    }

}
