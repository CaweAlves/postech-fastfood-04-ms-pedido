package org.fiap.fastfoodpedidos.application.port.driven;

import org.fiap.fastfoodpedidos.domain.model.Cliente;

public interface SalvarCliente {

    Cliente execute(Cliente cliente);
}
