package org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.repository;

import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.PedidoEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface PedidoRepositoryWithBagRelationships {
    Optional<PedidoEntity> fetchBagRelationships(Optional<PedidoEntity> pedido);

    List<PedidoEntity> fetchBagRelationships(List<PedidoEntity> pedidoEntities);

    Page<PedidoEntity> fetchBagRelationships(Page<PedidoEntity> pedidos);

}
