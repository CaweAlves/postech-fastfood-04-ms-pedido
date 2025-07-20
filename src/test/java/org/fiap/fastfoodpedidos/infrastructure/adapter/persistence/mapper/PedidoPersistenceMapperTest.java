package org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.mapper;

import org.fiap.fastfoodpedidos.domain.enumeration.PedidoStatus;
import org.fiap.fastfoodpedidos.domain.model.Pedido;
import org.fiap.fastfoodpedidos.domain.model.PedidoProduto;
import org.fiap.fastfoodpedidos.domain.model.Produto;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.PedidoEntity;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.PedidoProdutoEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Teste unitário para a classe PedidoPersistenceMapper.
 * Foco: Validar a lógica de mapeamento entre o modelo de domínio e a entidade de persistência.
 */
class PedidoPersistenceMapperTest {

    private final PedidoPersistenceMapper mapper = Mappers.getMapper(PedidoPersistenceMapper.class);

    @Test
    void deveMapearPedidoDeDominioParaEntidade() {
        Produto produtoDominio = Produto.builder().id(10).build();
        PedidoProduto pedidoProdutoDominio = PedidoProduto.builder()
                .id(100)
                .produto(produtoDominio)
                .quantidade(2)
                .valorUnitarioProdutoMomentoVenda(new BigDecimal("10.00"))
                .build();

        Pedido pedidoDominio = Pedido.builder()
                .id(1)
                .status(PedidoStatus.EM_PREPARACAO)
                .total(new BigDecimal("20.00"))
                .pedidoProdutos(Set.of(pedidoProdutoDominio))
                .build();

        PedidoEntity pedidoEntity = mapper.toEntity(pedidoDominio);

        assertThat(pedidoEntity).isNotNull();
        assertThat(pedidoEntity.getId()).isEqualTo(1);
        assertThat(pedidoEntity.getStatus()).isEqualTo(PedidoStatus.EM_PREPARACAO);
        assertThat(pedidoEntity.getTotal()).isEqualByComparingTo("20.00");
        assertThat(pedidoEntity.getPedidoProdutos()).hasSize(1);

        PedidoProdutoEntity pedidoProdutoEntity = pedidoEntity.getPedidoProdutos().iterator().next();
        assertThat(pedidoProdutoEntity.getId()).isEqualTo(100);
        assertThat(pedidoProdutoEntity.getProdutoId()).isEqualTo(10);
        assertThat(pedidoProdutoEntity.getQuantidade()).isEqualTo(2);
    }

    @Test
    void deveMapearPedidoDeEntidadeParaDominio() {
        PedidoProdutoEntity pedidoProdutoEntity = PedidoProdutoEntity.builder()
                .id(100)
                .produtoId(10)
                .quantidade(2)
                .valorUnitarioProdutoMomentoVenda(new BigDecimal("10.00"))
                .build();

        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setId(1);
        pedidoEntity.setStatus(PedidoStatus.EM_PREPARACAO);
        pedidoEntity.setTotal(new BigDecimal("20.00"));
        pedidoEntity.setPedidoProdutos(Set.of(pedidoProdutoEntity));

        Pedido pedidoDominio = mapper.toDomain(pedidoEntity);

        assertThat(pedidoDominio).isNotNull();
        assertThat(pedidoDominio.getId()).isEqualTo(1);
        assertThat(pedidoDominio.getStatus()).isEqualTo(PedidoStatus.EM_PREPARACAO);
        assertThat(pedidoDominio.getTotal()).isEqualByComparingTo("20.00");
        assertThat(pedidoDominio.getPedidoProdutos()).hasSize(1);

        PedidoProduto pedidoProdutoDominio = pedidoDominio.getPedidoProdutos().iterator().next();
        assertThat(pedidoProdutoDominio.getId()).isEqualTo(100);
        assertThat(pedidoProdutoDominio.getProduto()).isNotNull();
        assertThat(pedidoProdutoDominio.getProduto().getId()).isEqualTo(10);
        assertThat(pedidoProdutoDominio.getQuantidade()).isEqualTo(2);
    }
}