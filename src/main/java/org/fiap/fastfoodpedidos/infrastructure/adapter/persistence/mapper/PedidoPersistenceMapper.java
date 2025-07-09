package org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.mapper;

import org.fiap.fastfoodpedidos.domain.model.Categoria;
import org.fiap.fastfoodpedidos.domain.model.Pedido;
import org.fiap.fastfoodpedidos.domain.model.PedidoProduto;
import org.fiap.fastfoodpedidos.domain.model.Produto;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.CategoriaEntity;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.PedidoEntity;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.PedidoProdutoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PedidoPersistenceMapper {
    @Named("mapPedido")
    @Mapping(target = "pedidoProdutos", qualifiedByName = "mapPedidoProdutos")
    Pedido toDomain(PedidoEntity entity);

    @Mapping(target = "pedidoProdutos", qualifiedByName = "mapPedidoProdutos")
    PedidoEntity toEntity(Pedido pedido);

    @Named("mapPedidoProdutos")
    default PedidoProduto mapPedidoProduto(PedidoProdutoEntity entity) {
        if (entity == null) {
            return null;
        }

        CategoriaEntity categoria = entity.getProduto().getCategoria();
        return PedidoProduto.builder()
                .id(entity.getId())
                .quantidade(entity.getQuantidade())
                .valorUnitarioProdutoMomentoVenda(entity.getValorUnitarioProdutoMomentoVenda())
                .produto(entity.getProduto() != null ?
                        Produto.builder()
                                .id(entity.getProduto().getId())
                                .nome(entity.getProduto().getNome())
                                .descricao(entity.getProduto().getDescricao())
                                .preco(entity.getProduto().getPreco())
                                .categoria(criarCategoria(categoria))
                                .build()
                        : null)
                .build();
    }

    private static Categoria criarCategoria(CategoriaEntity categoria) {
        if (categoria == null) {
            return null;
        }
        return Categoria.builder()
                .id(categoria.getId())
                .nome(categoria.getNome())
                .descricao(categoria.getDescricao())
                .build();
    }

    @Named("mapPedidoProdutos")
    default PedidoProdutoEntity mapPedidoProdutoEntity(PedidoProduto pedidoProduto) {
        if (pedidoProduto == null) {
            return null;
        }

        return PedidoProdutoEntity.builder()
                .id(pedidoProduto.getId())
                .quantidade(pedidoProduto.getQuantidade())
                .valorUnitarioProdutoMomentoVenda(pedidoProduto.getValorUnitarioProdutoMomentoVenda())
                .produto(pedidoProduto.getProduto() != null ?
                        org.fiap.fastfood.infrastructure.adapter.persistence.entity.ProdutoEntity.builder()
                                .id(pedidoProduto.getProduto().getId())
                                .build()
                        : null)
                .build();
    }
}
