package org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.repository;

import org.fiap.fastfoodpedidos.domain.enumeration.PedidoStatus;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.PedidoEntity;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.PedidoProdutoEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Teste de Integração focado na PedidoRepository.
 * Foco: Validar as consultas personalizadas e os métodos default com Eager Loading.
 */
@DataJpaTest
class PedidoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Test
    void deveBuscarUmPedidoComRelacionamentos() {
        PedidoEntity pedido = new PedidoEntity();
        pedido.setStatus(PedidoStatus.RECEBIDO);
        pedido.setTotal(new BigDecimal("50.00"));

        PedidoProdutoEntity produto1 = PedidoProdutoEntity.builder().produtoId(101).quantidade(1).valorUnitarioProdutoMomentoVenda(new BigDecimal("20.00")).build();
        PedidoProdutoEntity produto2 = PedidoProdutoEntity.builder().produtoId(102).quantidade(2).valorUnitarioProdutoMomentoVenda(new BigDecimal("15.00")).build();

        pedido.setPedidoProdutos(Set.of(produto1, produto2));

        entityManager.persist(pedido);
        entityManager.flush();

        PedidoEntity pedidoEncontrado = pedidoRepository.findOneWithEagerRelationships(pedido.getId()).orElseThrow();

        assertThat(pedidoEncontrado).isNotNull();
        assertThat(pedidoEncontrado.getId()).isEqualTo(pedido.getId());
        assertThat(pedidoEncontrado.getPedidoProdutos()).hasSize(2);
    }

    @Test
    void deveBuscarTodosOsPedidosComRelacionamentos() {
        PedidoEntity pedido1 = new PedidoEntity();
        pedido1.setStatus(PedidoStatus.RECEBIDO);
        pedido1.setTotal(new BigDecimal("20.00"));
        pedido1.setPedidoProdutos(Set.of(PedidoProdutoEntity.builder().produtoId(101).quantidade(1).valorUnitarioProdutoMomentoVenda(new BigDecimal("20.00")).build()));

        PedidoEntity pedido2 = new PedidoEntity();
        pedido2.setStatus(PedidoStatus.EM_PREPARACAO);
        pedido2.setTotal(new BigDecimal("30.00"));
        pedido2.setPedidoProdutos(Set.of(PedidoProdutoEntity.builder().produtoId(102).quantidade(2).valorUnitarioProdutoMomentoVenda(new BigDecimal("15.00")).build()));

        entityManager.persist(pedido1);
        entityManager.persist(pedido2);
        entityManager.flush();

        List<PedidoEntity> pedidosEncontrados = pedidoRepository.findAllWithEagerRelationships();

        assertThat(pedidosEncontrados).hasSize(2);
        assertThat(pedidosEncontrados.get(0).getPedidoProdutos()).hasSize(1);
        assertThat(pedidosEncontrados.get(1).getPedidoProdutos()).hasSize(1);
    }

    @Test
    void deveBuscarPaginaDePedidosComRelacionamentos() {
        PedidoEntity pedido1 = new PedidoEntity().status(PedidoStatus.RECEBIDO).total(new BigDecimal("10.00"));
        pedido1.setPedidoProdutos(Set.of(PedidoProdutoEntity.builder().produtoId(101).quantidade(1).valorUnitarioProdutoMomentoVenda(BigDecimal.TEN).build()));

        PedidoEntity pedido2 = new PedidoEntity().status(PedidoStatus.EM_PREPARACAO).total(new BigDecimal("20.00"));

        PedidoEntity pedido3 = new PedidoEntity().status(PedidoStatus.PRONTO).total(new BigDecimal("30.00"));
        pedido3.setPedidoProdutos(Set.of(PedidoProdutoEntity.builder().produtoId(102).quantidade(2).valorUnitarioProdutoMomentoVenda(new BigDecimal("15.00")).build()));

        entityManager.persist(pedido1);
        entityManager.persist(pedido2);
        entityManager.persist(pedido3);
        entityManager.flush();

        Pageable pageable = PageRequest.of(0, 2);

        Page<PedidoEntity> paginaDePedidos = pedidoRepository.findAllWithEagerRelationships(pageable);

        assertThat(paginaDePedidos).isNotNull();
        assertThat(paginaDePedidos.getTotalElements()).isEqualTo(3);
        assertThat(paginaDePedidos.getContent()).hasSize(2);

        PedidoEntity primeiroPedidoDaPagina = paginaDePedidos.getContent().get(0);
        assertThat(primeiroPedidoDaPagina.getId()).isEqualTo(pedido1.getId());
        assertThat(primeiroPedidoDaPagina.getPedidoProdutos()).hasSize(1);
    }
}