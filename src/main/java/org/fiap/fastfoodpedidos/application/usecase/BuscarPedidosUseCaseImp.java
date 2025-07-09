package org.fiap.fastfoodpedidos.application.usecase;

import lombok.AllArgsConstructor;
import org.fiap.fastfoodpedidos.application.port.driven.ConsultarPedidos;
import org.fiap.fastfoodpedidos.application.port.driver.BuscarPedidosUseCase;
import org.fiap.fastfoodpedidos.domain.model.Pedido;

import java.util.List;

@AllArgsConstructor
public class BuscarPedidosUseCaseImp implements BuscarPedidosUseCase {

    private final ConsultarPedidos consultarPedidos;

    @Override
    public List<Pedido> execute() {
        return consultarPedidos.execute();
    }

}
