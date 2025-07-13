package org.fiap.fastfoodpedidos.application.usecase;

import lombok.AllArgsConstructor;
import org.fiap.fastfoodpedidos.application.port.driven.SalvarCliente;
import org.fiap.fastfoodpedidos.application.port.driver.CriarClienteUseCase;
import org.fiap.fastfoodpedidos.domain.model.Cliente;

@AllArgsConstructor
public class CriarClienteUseCaseImp implements CriarClienteUseCase {

    private final SalvarCliente salvarCliente;

    @Override
    public Cliente execute(Cliente cliente) {
        return salvarCliente.execute(cliente);
    }
}
