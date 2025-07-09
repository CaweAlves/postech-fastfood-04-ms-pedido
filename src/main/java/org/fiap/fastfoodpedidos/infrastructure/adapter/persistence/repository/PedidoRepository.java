package org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.repository;

import org.fiap.fastfoodpedidos.domain.enumeration.PedidoStatus;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.PedidoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Pedido entity.
 * <p>
 * When extending this class, extend PedidoRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface PedidoRepository extends PedidoRepositoryWithBagRelationships, JpaRepository<PedidoEntity, Integer> {

    List<PedidoEntity> findByStatusIn(List<PedidoStatus> statusList);

    default Optional<PedidoEntity> findOneWithEagerRelationships(Integer id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<PedidoEntity> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<PedidoEntity> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
