package org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.repository;


import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {
}
