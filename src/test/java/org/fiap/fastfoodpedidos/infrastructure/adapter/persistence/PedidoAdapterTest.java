package org.fiap.fastfoodpedidos.infrastructure.adapter.persistence;

import org.fiap.fastfoodpedidos.domain.enumeration.PedidoStatus;
import org.fiap.fastfoodpedidos.domain.model.Pedido;
import org.fiap.fastfoodpedidos.domain.model.PedidoProduto;
import org.fiap.fastfoodpedidos.domain.model.Produto;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.PedidoEntity;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.mapper.PedidoPersistenceMapper;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({PedidoAdapter.class, PedidoAdapterTest.MapperTestConfig.class})
class PedidoAdapterTest {

    @TestConfiguration
    static class MapperTestConfig {
        /**
         * Este bean fornece a implementação concreta do PedidoPersistenceMapper
         * para o contexto de teste do Spring.
         */
        @Bean
        public PedidoPersistenceMapper pedidoPersistenceMapper() {
            return Mappers.getMapper(PedidoPersistenceMapper.class);
        }
    }

    @Autowired
    private PedidoAdapter pedidoAdapter;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Test
    void deveSalvarUmPedidoComSucesso() {
        Produto produtoParaPedido = Produto.builder().id(1).build();

        PedidoProduto pedidoProduto = PedidoProduto.builder().produto(produtoParaPedido).quantidade(2).valorUnitarioProdutoMomentoVenda(new BigDecimal("15.00")).build();

        Pedido pedidoParaSalvar = Pedido.builder().status(PedidoStatus.AGUARDANDO_PAGAMENTO).total(new BigDecimal("30.00")).pedidoProdutos(Set.of(pedidoProduto)).build();

        Pedido pedidoSalvo = pedidoAdapter.execute(pedidoParaSalvar);

        assertThat(pedidoSalvo).isNotNull();
        assertThat(pedidoSalvo.getId()).isNotNull();

        Optional<PedidoEntity> pedidoEntityOptional = pedidoRepository.findById(pedidoSalvo.getId());
        assertThat(pedidoEntityOptional).isPresent();
        PedidoEntity pedidoEntity = pedidoEntityOptional.get();

        assertThat(pedidoEntity.getStatus()).isEqualTo(PedidoStatus.AGUARDANDO_PAGAMENTO);
        assertThat(pedidoEntity.getTotal()).isEqualByComparingTo("30.00");
        assertThat(pedidoEntity.getPedidoProdutos()).hasSize(1);
        assertThat(pedidoEntity.getPedidoProdutos().iterator().next().getProdutoId()).isEqualTo(1);
    }

    @Test
    void deveConsultarUmPedidoPorIdComSucesso() {
        PedidoEntity pedidoPreExistente = new PedidoEntity();
        pedidoPreExistente.setStatus(PedidoStatus.RECEBIDO);
        pedidoPreExistente.setTotal(new BigDecimal("99.90"));
        PedidoEntity pedidoEntitySalvo = pedidoRepository.save(pedidoPreExistente);
        Integer idDoPedido = pedidoEntitySalvo.getId();

        Pedido pedidoEncontrado = pedidoAdapter.execute(idDoPedido);

        assertThat(pedidoEncontrado).isNotNull();
        assertThat(pedidoEncontrado.getId()).isEqualTo(idDoPedido);
        assertThat(pedidoEncontrado.getStatus()).isEqualTo(PedidoStatus.RECEBIDO);
        assertThat(pedidoEncontrado.getTotal()).isEqualByComparingTo("99.90");
    }

    @Test
    void deveRetornarApenasPedidosEmAndamento() {
        pedidoRepository.save(new PedidoEntity().status(PedidoStatus.RECEBIDO).total(BigDecimal.TEN));
        pedidoRepository.save(new PedidoEntity().status(PedidoStatus.EM_PREPARACAO).total(BigDecimal.TEN));
        pedidoRepository.save(new PedidoEntity().status(PedidoStatus.PRONTO).total(BigDecimal.TEN));

        pedidoRepository.save(new PedidoEntity().status(PedidoStatus.AGUARDANDO_PAGAMENTO).total(BigDecimal.TEN));
        pedidoRepository.save(new PedidoEntity().status(PedidoStatus.FINALIZADO).total(BigDecimal.TEN));
        pedidoRepository.save(new PedidoEntity().status(PedidoStatus.CANCELADO).total(BigDecimal.TEN));

        List<Pedido> pedidosEmAndamento = pedidoAdapter.consultarPedidosEmAndamento();

        assertThat(pedidosEmAndamento).isNotNull();
        assertThat(pedidosEmAndamento).hasSize(3);

        assertThat(pedidosEmAndamento).allMatch(pedido -> pedido.getStatus() == PedidoStatus.RECEBIDO || pedido.getStatus() == PedidoStatus.EM_PREPARACAO || pedido.getStatus() == PedidoStatus.PRONTO);
    }
}