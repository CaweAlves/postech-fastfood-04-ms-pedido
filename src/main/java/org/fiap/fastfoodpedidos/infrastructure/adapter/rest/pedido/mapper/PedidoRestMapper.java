package org.fiap.fastfoodpedidos.infrastructure.adapter.rest.pedido.mapper;

import org.fiap.fastfoodpedidos.domain.model.Cliente;
import org.fiap.fastfoodpedidos.domain.model.Pedido;
import org.fiap.fastfoodpedidos.domain.model.PedidoProduto;
import org.fiap.fastfoodpedidos.domain.model.Produto;
import org.fiap.fastfoodpedidos.infrastructure.adapter.rest.pedido.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PedidoRestMapper {

    @Mapping(target = "cliente", source = "cliente", qualifiedByName = "mapCliente")
    @Mapping(target = "pedidoProdutos", source = "produtos", qualifiedByName = "mapProdutos")
    Pedido toPedido(RegistrarPedidoRequest registrarPedidoRequest);

    RegistrarPedidoResponse toRegistrarPedidoResponse(Pedido pedido);

    DetalhePedidoResponse toDetalhePedidoResponse(Pedido pedido);

    @Mapping(target = "tempoDeEspera", source = "dataHoraPedidoRecebido", qualifiedByName = "mapTempoDeEspera")
    DetalhePedidoEmPreparacaoResponse toDetalhePedidoEmPreparacaoResponse(Pedido pedido);

    @Named("mapCliente")
    default Cliente mapCliente(Integer value) {
        if (value == null) {
            return null;
        }
        return Cliente.builder().id(value).build();
    }

    @Named("mapTempoDeEspera")
    default String mapTempoDeEspera(LocalDateTime dataHoraPedidoRecebido) {
        if (dataHoraPedidoRecebido == null) {
            return null;
        }
        long hours = ChronoUnit.HOURS.between(dataHoraPedidoRecebido, LocalDateTime.now());
        long minutes = ChronoUnit.MINUTES.between(dataHoraPedidoRecebido, LocalDateTime.now().minusHours(hours));
        return "%02d:%02d".formatted(hours, minutes);
    }

    @Named("mapProdutos")
    default Set<PedidoProduto> mapProdutos(Set<ProdutoQuantidadePedidoRequest> values) {
        if (values == null) {
            return null;
        }
        return values.stream()
                .map(pp -> PedidoProduto.builder()
                        .produto(Produto.builder().id(pp.produto()).build())
                        .quantidade(pp.quantidade())
                        .build())
                .collect(Collectors.toSet());
    }
}
