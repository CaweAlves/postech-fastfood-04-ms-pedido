package org.fiap.fastfoodpedidos.infrastructure.adapter.persistence;


import java.util.List;

import org.fiap.fastfoodpedidos.application.port.driven.ManipularPagamento;
import org.fiap.fastfoodpedidos.domain.exception.EntidadeNaoEncontradaException;
import org.fiap.fastfoodpedidos.domain.model.Pagamento;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.PagamentoEntity;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.mapper.PagamentoPersistenceMapper;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.repository.PagamentoRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PagamentoAdapter implements ManipularPagamento {

    private final PagamentoRepository pagamentoRepository;

    private final PagamentoPersistenceMapper pagamentoPersistenceMapper;

    @Override
    public Pagamento criarPagamento(Pagamento pagamento) {
        PagamentoEntity pagamentoEntity = this.pagamentoPersistenceMapper.toEntity(pagamento);
        pagamentoEntity = this.pagamentoRepository.save(pagamentoEntity);
        return this.pagamentoPersistenceMapper.toDomain(pagamentoEntity);
    }

    @Override
    public Pagamento buscarPagamentoPorIdExterno(String idExterno) {
        PagamentoEntity pagamentoEntity = this.pagamentoRepository.findByIdExterno(idExterno)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(Pagamento.class.getName()));
        return this.pagamentoPersistenceMapper.toDomain(pagamentoEntity);
    }

    @Override
    public Pagamento alterarPagamento(Pagamento pagamento) {
        this.pagamentoRepository.findById(pagamento.getId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException(Pagamento.class.getName()));

        PagamentoEntity pagamentoEntity = this.pagamentoPersistenceMapper.toEntity(pagamento);
        pagamentoEntity = this.pagamentoRepository.save(pagamentoEntity);
        return this.pagamentoPersistenceMapper.toDomain(pagamentoEntity);
    }

    @Override
    public List<Pagamento> buscarPagamentosPorIdPedido(Integer idPedido) {
        List<PagamentoEntity> pagamentoEntities = this.pagamentoRepository.findByPedidoId(idPedido);
        return pagamentoEntities.stream()
                .map(this.pagamentoPersistenceMapper::toDomain)
                .toList();
    }
}
