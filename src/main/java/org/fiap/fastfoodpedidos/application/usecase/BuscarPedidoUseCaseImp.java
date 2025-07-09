package org.fiap.fastfoodpedidos.application.usecase;

import lombok.AllArgsConstructor;
import org.fiap.fastfoodpedidos.application.port.driven.ConsultarPedido;
import org.fiap.fastfoodpedidos.application.port.driver.BuscarPedidoUseCase;
import org.fiap.fastfoodpedidos.domain.model.Pedido;

@AllArgsConstructor
public class BuscarPedidoUseCaseImp implements BuscarPedidoUseCase {

    private final ConsultarPedido consultarPedido;

    @Override
    public Pedido execute(Integer id) {
        return consultarPedido.execute(id);
    }

}
