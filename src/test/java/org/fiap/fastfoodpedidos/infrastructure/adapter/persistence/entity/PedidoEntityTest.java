package org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity;

import org.fiap.fastfoodpedidos.domain.enumeration.PedidoStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Teste unitário para a classe PedidoEntity.
 * Foco: Validar construtores, getters, setters, equals, hashCode e toString.
 */
class PedidoEntityTest {

    @Test
    void deveTestarSettersEGetters() {
        PedidoEntity pedido = new PedidoEntity();
        ClienteEntity cliente = new ClienteEntity();
        cliente.setId(1);
        Set<PedidoProdutoEntity> produtos = new HashSet<>();
        produtos.add(new PedidoProdutoEntity());

        pedido.setId(123);
        pedido.setStatus(PedidoStatus.RECEBIDO);
        pedido.setTotal(new BigDecimal("99.90"));
        pedido.setCliente(cliente);
        pedido.setPedidoProdutos(produtos);
        pedido.setDataHoraPedidoRecebido(java.time.LocalDateTime.now());

        assertThat(pedido.getId()).isEqualTo(123);
        assertThat(pedido.getStatus()).isEqualTo(PedidoStatus.RECEBIDO);
        assertThat(pedido.getTotal()).isEqualByComparingTo("99.90");
        assertThat(pedido.getCliente().getId()).isEqualTo(1);
        assertThat(pedido.getPedidoProdutos()).hasSize(1);
        assertThat(pedido.getDataHoraPedidoRecebido()).isNotNull();
    }

    @Test
    void deveTestarMetodosChaining() {
        PedidoEntity pedido = new PedidoEntity()
                .id(123)
                .status(PedidoStatus.FINALIZADO)
                .total(BigDecimal.TEN)
                .cliente(new ClienteEntity())
                .pedidoProdutos(new HashSet<>());

        assertThat(pedido.getId()).isEqualTo(123);
        assertThat(pedido.getStatus()).isEqualTo(PedidoStatus.FINALIZADO);
    }

    @Test
    void deveTestarEqualsHashCodeEToString() {
        PedidoEntity pedido1 = new PedidoEntity();
        pedido1.setId(1);

        PedidoEntity pedido2 = new PedidoEntity();
        pedido2.setId(1);

        PedidoEntity pedido3 = new PedidoEntity();
        pedido3.setId(2);

        PedidoEntity pedido4 = new PedidoEntity();

        assertTrue(pedido1.equals(pedido2));
        assertTrue(pedido1.equals(pedido1));
        assertFalse(pedido1.equals(pedido3));
        assertFalse(pedido1.equals(null));
        assertFalse(pedido1.equals(new Object()));
        assertFalse(pedido1.equals(pedido4));
        assertFalse(pedido4.equals(pedido1));

        assertThat(pedido1.hashCode()).isEqualTo(pedido3.hashCode());

        assertThat(pedido1.toString()).contains("id=1");
    }
}