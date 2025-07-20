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
class RemoverProdutoPedidoUseCaseImplTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private RemoverProdutoPedidoUseCaseImpl removerProdutoPedidoUseCase;

    @Test
    void deveRemoverProdutoDoPedido_QuandoPedidoExiste() {
        int pedidoId = 1;
        PedidoEntity pedidoExistente = new PedidoEntity();
        PedidoEntity spyPedido = spy(pedidoExistente);
        PedidoProdutoEntity produtoParaRemover = new PedidoProdutoEntity();

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(spyPedido));

        removerProdutoPedidoUseCase.removerProduto(pedidoId, produtoParaRemover);

        verify(pedidoRepository, times(1)).findById(pedidoId);
        verify(spyPedido, times(1)).removePedidoProduto(produtoParaRemover);
    }

    @Test
    void naoDeveFazerNada_QuandoPedidoNaoExiste() {
        int pedidoIdInexistente = 99;
        PedidoProdutoEntity produtoParaRemover = new PedidoProdutoEntity();

        when(pedidoRepository.findById(pedidoIdInexistente)).thenReturn(Optional.empty());

        removerProdutoPedidoUseCase.removerProduto(pedidoIdInexistente, produtoParaRemover);

        verify(pedidoRepository, times(1)).findById(pedidoIdInexistente);
    }
}