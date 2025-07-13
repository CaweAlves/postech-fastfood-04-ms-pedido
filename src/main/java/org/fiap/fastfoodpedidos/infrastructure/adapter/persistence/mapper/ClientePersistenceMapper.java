package org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.mapper;


import org.fiap.fastfoodpedidos.domain.model.Cliente;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.ClienteEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientePersistenceMapper {

    Cliente toDomain(ClienteEntity entity);

    ClienteEntity toEntity(Cliente domain);
}
