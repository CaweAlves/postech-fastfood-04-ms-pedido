package org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.repository;

import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.PedidoProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoProdutoRepository extends JpaRepository<PedidoProdutoEntity, Integer> {

    List<PedidoProdutoEntity> findByProdutoId(Integer produtoId);

}
