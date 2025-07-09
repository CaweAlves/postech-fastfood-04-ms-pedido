package org.fiap.fastfoodpedidos.application.usecase;

import lombok.AllArgsConstructor;
import org.fiap.fastfoodpedidos.application.port.driven.ConsultarPedidosEmAndamento;
import org.fiap.fastfoodpedidos.application.port.driver.BuscarPedidosEmAndamentoUseCase;
import org.fiap.fastfoodpedidos.domain.model.Pedido;

import java.util.List;

@AllArgsConstructor
public class BuscarPedidosEmAndamentoUseCaseImp implements BuscarPedidosEmAndamentoUseCase {

    private final ConsultarPedidosEmAndamento consultarPedidos;

    @Override
    public List<Pedido> execute() {
        return consultarPedidos.consultarPedidosEmAndamento();
    }

}
