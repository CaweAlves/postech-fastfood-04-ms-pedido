package org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Teste unitário para a classe PedidoProdutoEntity.
 * Foco: Validar construtores, getters, setters, equals, hashCode e toString.
 */
class PedidoProdutoEntityTest {

    @Test
    void deveTestarSettersEGetters() {
        PedidoProdutoEntity pedidoProduto = new PedidoProdutoEntity();
        PedidoEntity pedido = new PedidoEntity();
        pedido.setId(1);
        BigDecimal valor = new BigDecimal("12.345");
        BigDecimal valorEsperado = new BigDecimal("12.35");

        pedidoProduto.setId(100);
        pedidoProduto.setProdutoId(10);
        pedidoProduto.setQuantidade(3);
        pedidoProduto.setPedido(pedido);
        pedidoProduto.setValorUnitarioProdutoMomentoVenda(valor);

        assertThat(pedidoProduto.getId()).isEqualTo(100);
        assertThat(pedidoProduto.getProdutoId()).isEqualTo(10);
        assertThat(pedidoProduto.getQuantidade()).isEqualTo(3);
        assertThat(pedidoProduto.getPedido().getId()).isEqualTo(1);
        assertThat(pedidoProduto.getValorUnitarioProdutoMomentoVenda()).isEqualTo(valorEsperado);
    }

    @Test
    void deveTestarMetodosChaining() {
        PedidoProdutoEntity pedidoProduto = new PedidoProdutoEntity()
                .id(100)
                .quantidade(5)
                .produtoId(20)
                .pedido(new PedidoEntity());

        assertThat(pedidoProduto.getId()).isEqualTo(100);
        assertThat(pedidoProduto.getQuantidade()).isEqualTo(5);
        assertThat(pedidoProduto.getProdutoId()).isEqualTo(20);
        assertThat(pedidoProduto.getPedido()).isNotNull();
    }

    @Test
    void setValorUnitarioDeveArredondarCorretamente() {
        PedidoProdutoEntity pedidoProduto = new PedidoProdutoEntity();

        pedidoProduto.setValorUnitarioProdutoMomentoVenda(new BigDecimal("9.994"));

        assertThat(pedidoProduto.getValorUnitarioProdutoMomentoVenda()).isEqualByComparingTo("9.99");

        pedidoProduto.setValorUnitarioProdutoMomentoVenda(new BigDecimal("9.995"));

        assertThat(pedidoProduto.getValorUnitarioProdutoMomentoVenda()).isEqualByComparingTo("10.00");
    }

    @Test
    void deveTestarEqualsHashCodeEToString() {
        PedidoProdutoEntity pp1 = new PedidoProdutoEntity();
        pp1.setId(1);

        PedidoProdutoEntity pp2 = new PedidoProdutoEntity();
        pp2.setId(1);

        PedidoProdutoEntity pp3 = new PedidoProdutoEntity();
        pp3.setId(2);

        PedidoProdutoEntity pp4 = new PedidoProdutoEntity();

        assertTrue(pp1.equals(pp2));
        assertTrue(pp1.equals(pp1));
        assertFalse(pp1.equals(pp3));
        assertFalse(pp1.equals(null));
        assertFalse(pp1.equals(new Object()));
        assertFalse(pp1.equals(pp4));
        assertFalse(pp4.equals(pp1));

        assertThat(pp1.hashCode()).isEqualTo(pp3.hashCode());

        assertThat(pp1.toString()).contains("id=1", "quantidade=null");
    }
}