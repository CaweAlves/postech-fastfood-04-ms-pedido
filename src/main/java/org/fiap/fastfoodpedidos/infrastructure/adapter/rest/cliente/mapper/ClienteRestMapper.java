package org.fiap.fastfoodpedidos.infrastructure.adapter.rest.cliente.mapper;

import org.fiap.fastfoodpedidos.domain.model.Cliente;
import org.fiap.fastfoodpedidos.infrastructure.adapter.rest.cliente.dto.RegistrarClienteRequest;
import org.fiap.fastfoodpedidos.infrastructure.adapter.rest.cliente.dto.RegistrarClienteResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteRestMapper {

    Cliente toCliente(RegistrarClienteRequest registrarClienteRequest);

    RegistrarClienteRequest toRegistrarClienteRequest(Cliente cliente);

    RegistrarClienteResponse toRegistrarClienteResponse(Cliente cliente);
}
