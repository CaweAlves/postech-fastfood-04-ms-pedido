package org.fiap.fastfoodpedidos.infrastructure.adapter.persistence;

import lombok.RequiredArgsConstructor;
import org.fiap.fastfoodpedidos.application.port.driven.*;
import org.fiap.fastfoodpedidos.domain.enumeration.PedidoStatus;
import org.fiap.fastfoodpedidos.domain.model.Pedido;
import org.fiap.fastfoodpedidos.domain.model.Produto;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.PedidoEntity;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.PedidoProdutoEntity;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.mapper.PedidoPersistenceMapper;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.repository.PedidoProdutoRepository;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.repository.PedidoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
public class PedidoAdapter implements SalvarPedido, ConsultarPedido, BuscarPedidosPorUmProduto, ConsultarPedidos, ConsultarPedidosEmAndamento {

    private final PedidoRepository pedidoRepository;
    private final PedidoPersistenceMapper pedidoPersistenceMapper;
    private final PedidoProdutoRepository pedidoProdutoRepository;

    @Override
    public Pedido execute(Integer id) {
        PedidoEntity pedidoEntity = this.pedidoRepository.findOneWithEagerRelationships(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "O pedido não foi encontrado."));
        return this.pedidoPersistenceMapper.toDomain(pedidoEntity);
    }

    @Override
    public Pedido execute(Pedido pedido) {
        PedidoEntity pedidoEntity = this.pedidoPersistenceMapper.toEntity(pedido);
        pedidoEntity = this.pedidoRepository.save(pedidoEntity);
        return this.pedidoPersistenceMapper.toDomain(pedidoEntity);
    }

    @Override
    public List<Pedido> execute(Produto produto) {
        List<PedidoProdutoEntity> pedidoProdutoList = pedidoProdutoRepository.findByProdutoId(produto.getId());
        return pedidoProdutoList.stream()
                .map(pedidoProduto -> pedidoPersistenceMapper.toDomain(pedidoProduto.getPedido()))
                .distinct()
                .toList();
    }

    @Override
    public List<Pedido> execute() {
        return pedidoRepository.findAll().stream().map(this.pedidoPersistenceMapper::toDomain).toList();
    }

    @Override
    public List<Pedido> consultarPedidosEmAndamento() {
        List<PedidoStatus> statusList = List.of(PedidoStatus.RECEBIDO, PedidoStatus.EM_PREPARACAO, PedidoStatus.PRONTO);
        return pedidoRepository.findByStatusIn(statusList).stream().map(this.pedidoPersistenceMapper::toDomain).toList();
    }
}
