package org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.PedidoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class PedidoRepositoryWithBagRelationshipsImpl implements PedidoRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<PedidoEntity> fetchBagRelationships(Optional<PedidoEntity> pedido) {
        // Este método agora chama a versão corrigida de fetchProdutos
        return pedido.map(this::fetchProdutos);
    }

    @Override
    public Page<PedidoEntity> fetchBagRelationships(Page<PedidoEntity> pedidos) {
        return new PageImpl<>(fetchBagRelationships(pedidos.getContent()), pedidos.getPageable(), pedidos.getTotalElements());
    }

    @Override
    public List<PedidoEntity> fetchBagRelationships(List<PedidoEntity> pedidoEntities) {
        return Optional.of(pedidoEntities).map(this::fetchProdutos).orElse(Collections.emptyList());
    }

    PedidoEntity fetchProdutos(PedidoEntity result) {
        return entityManager
                .createQuery("select pedido from PedidoEntity pedido left join fetch pedido.pedidoProdutos where pedido = :pedido", PedidoEntity.class)
                .setParameter("pedido", result)
                .setHint("hibernate.query.passDistinctThrough", false)
                .getSingleResult();
    }

    List<PedidoEntity> fetchProdutos(List<PedidoEntity> pedidoEntities) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, pedidoEntities.size()).forEach(index -> order.put(pedidoEntities.get(index).getId(), index));
        List<PedidoEntity> result = entityManager
                .createQuery("select distinct pedido from PedidoEntity pedido left join fetch pedido.pedidoProdutos where pedido in :pedidos", PedidoEntity.class)
                .setParameter("pedidos", pedidoEntities)
                .setHint("hibernate.query.passDistinctThrough", false)
                .getResultList();
        result.sort(Comparator.comparingInt(o -> order.get(o.getId())));
        return result;
    }
}