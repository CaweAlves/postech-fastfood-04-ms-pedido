package org.fiap.fastfoodpedidos.infrastructure.adapter.rest.cliente.dto;

public record RegistrarClienteResponse(
        Integer id,
        String nome,
        String email,
        String cpf
) {
}
