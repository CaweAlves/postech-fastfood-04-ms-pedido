package org.fiap.fastfoodpedidos.application.usecase;

import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.PedidoEntity;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.PedidoProdutoEntity;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdicionarProdutoAoPedidoUseCaseImplTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private AdicionarProdutoAoPedidoUseCaseImpl adicionarProdutoAoPedidoUseCase;

    @Test
    void deveAdicionarProdutoAoPedido_QuandoPedidoExiste() {
        int pedidoId = 1;
        PedidoEntity pedidoExistente = new PedidoEntity();
        PedidoEntity spyPedido = spy(pedidoExistente);
        PedidoProdutoEntity produtoParaAdicionar = new PedidoProdutoEntity();

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(spyPedido));

        adicionarProdutoAoPedidoUseCase.adicionarProduto(pedidoId, produtoParaAdicionar);

        verify(pedidoRepository, times(1)).findById(pedidoId);
        verify(spyPedido, times(1)).addPedidoProduto(produtoParaAdicionar);
    }

    @Test
    void naoDeveFazerNada_QuandoPedidoNaoExiste() {
        int pedidoIdInexistente = 99;
        PedidoProdutoEntity produtoParaAdicionar = new PedidoProdutoEntity();

        when(pedidoRepository.findById(pedidoIdInexistente)).thenReturn(Optional.empty());

        adicionarProdutoAoPedidoUseCase.adicionarProduto(pedidoIdInexistente, produtoParaAdicionar);

        verify(pedidoRepository, times(1)).findById(pedidoIdInexistente);
    }
}