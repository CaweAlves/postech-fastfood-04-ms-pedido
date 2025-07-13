package org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.mapper;

import org.fiap.fastfoodpedidos.domain.model.Pagamento;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.PagamentoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PedidoPersistenceMapper.class})
public interface PagamentoPersistenceMapper {

    Pagamento toDomain(PagamentoEntity pagamentoEntity);

    PagamentoEntity toEntity(Pagamento pagamento);
}