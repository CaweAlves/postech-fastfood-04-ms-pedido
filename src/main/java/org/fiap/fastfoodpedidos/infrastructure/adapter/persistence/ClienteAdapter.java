package org.fiap.fastfoodpedidos.infrastructure.adapter.persistence;

import lombok.RequiredArgsConstructor;
import org.fiap.fastfoodpedidos.application.port.driven.SalvarCliente;
import org.fiap.fastfoodpedidos.domain.model.Cliente;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.ClienteEntity;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.mapper.ClientePersistenceMapper;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.repository.ClienteRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteAdapter implements SalvarCliente {

    private final ClienteRepository clienteRepository;

    private final ClientePersistenceMapper clientePersistenceMapper;

    @Override
    public Cliente execute(Cliente cliente) {
        ClienteEntity clienteEntity = this.clientePersistenceMapper.toEntity(cliente);
        clienteEntity = this.clienteRepository.save(clienteEntity);
        return this.clientePersistenceMapper.toDomain(clienteEntity);
    }
}
