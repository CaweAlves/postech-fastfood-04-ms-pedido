package org.fiap.fastfoodpedidos.application.usecase;

import org.fiap.fastfoodpedidos.application.port.driven.BuscarProdutos;
import org.fiap.fastfoodpedidos.domain.model.Produto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarProdutosDisponiveisUseCaseImplTest {

    @Mock
    private BuscarProdutos buscarProdutos;

    @InjectMocks
    private ListarProdutosDisponiveisUseCaseImpl listarProdutosDisponiveisUseCase;

    @Test
    void deveRetornarListaDeProdutos_QuandoChamado() {
        Produto produto1 = new Produto(1, "Hambúrguer", "Delicioso", new BigDecimal("25.00"), null);
        Produto produto2 = new Produto(2, "Batata Frita", "Crocante", new BigDecimal("15.00"), null);
        List<Produto> listaDeProdutosEsperada = List.of(produto1, produto2);

        when(buscarProdutos.buscarTodos()).thenReturn(listaDeProdutosEsperada);

        List<Produto> resultado = listarProdutosDisponiveisUseCase.executar();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Hambúrguer", resultado.get(0).getNome());
        assertEquals("Batata Frita", resultado.get(1).getNome());

        verify(buscarProdutos, times(1)).buscarTodos();
    }
}