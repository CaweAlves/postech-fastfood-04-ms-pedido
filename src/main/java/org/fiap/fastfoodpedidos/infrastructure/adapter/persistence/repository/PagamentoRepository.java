package org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.repository;


import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.PagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PagamentoRepository extends JpaRepository<PagamentoEntity, Integer> {

    Optional<PagamentoEntity> findByIdExterno(String idExterno);

    List<PagamentoEntity> findByPedidoId(Integer idPedido);

}
