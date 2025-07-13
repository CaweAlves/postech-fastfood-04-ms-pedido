package org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.mapper;

import org.fiap.fastfoodpedidos.domain.model.Pedido;
import org.fiap.fastfoodpedidos.domain.model.PedidoProduto;
import org.fiap.fastfoodpedidos.domain.model.Produto;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.PedidoEntity;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.PedidoProdutoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PedidoPersistenceMapper {

    Pedido toDomain(PedidoEntity entity);

    PedidoEntity toEntity(Pedido pedido);

    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "produto", source = "produtoId", qualifiedByName = "mapProdutoIdToProduto")
    PedidoProduto toDomain(PedidoProdutoEntity entity);

    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "produtoId", source = "produto.id")
    PedidoProdutoEntity toEntity(PedidoProduto domain);

    @Named("mapProdutoIdToProduto")
    default Produto mapProdutoIdToProduto(Integer produtoId) {
        if (produtoId == null) {
            return null;
        }
        return Produto.builder().id(produtoId).build();
    }
}