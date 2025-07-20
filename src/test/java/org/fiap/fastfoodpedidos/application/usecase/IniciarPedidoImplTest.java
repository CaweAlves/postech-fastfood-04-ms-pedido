package org.fiap.fastfoodpedidos.application.usecase;

import org.fiap.fastfoodpedidos.domain.model.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class IniciarPedidoImplTest {

    @InjectMocks
    private IniciarPedidoImpl iniciarPedidoUseCase;

    @Test
    void deveRetornarOMesmoPedidoQueFoiRecebido() {
        Pedido pedidoDeEntrada = new Pedido();
        pedidoDeEntrada.setId(123);

        Pedido pedidoDeSaida = iniciarPedidoUseCase.iniciarPedido(pedidoDeEntrada);

        assertNotNull(pedidoDeSaida);
        assertEquals(pedidoDeEntrada, pedidoDeSaida);
        assertEquals(123, pedidoDeSaida.getId());
    }
}