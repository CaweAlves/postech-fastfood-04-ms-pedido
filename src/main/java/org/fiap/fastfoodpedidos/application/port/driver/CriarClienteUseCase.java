package org.fiap.fastfoodpedidos.application.port.driver;

import org.fiap.fastfoodpedidos.domain.model.Cliente;

public interface CriarClienteUseCase {

    Cliente execute(Cliente cliente);

}
