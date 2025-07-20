package org.fiap.fastfoodpedidos.application.usecase;

import org.fiap.fastfoodpedidos.application.port.driven.BuscarProdutoPeloIdUseCase;
import org.fiap.fastfoodpedidos.application.port.driven.SalvarPedido;
import org.fiap.fastfoodpedidos.domain.enumeration.PedidoStatus;
import org.fiap.fastfoodpedidos.domain.model.Pedido;
import org.fiap.fastfoodpedidos.domain.model.PedidoProduto;
import org.fiap.fastfoodpedidos.domain.model.Produto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Teste unitário para a classe CriarPedidoUseCaseImp.
 * Foco: Validar a lógica de negócio de criação de pedido em isolamento.
 */
@ExtendWith(MockitoExtension.class)
class CriarPedidoUseCaseImpTest {

    @Mock
    private SalvarPedido salvarPedido;

    @Mock
    private BuscarProdutoPeloIdUseCase buscarProdutoPeloIdUseCase;

    @InjectMocks
    private CriarPedidoUseCaseImp criarPedidoUseCase;

    private Pedido pedidoParaSalvar;
    private Produto produtoExistente;

    @BeforeEach
    void setUp() {
        produtoExistente = new Produto();
        produtoExistente.setId(1);
        produtoExistente.setNome("Hambúrguer");
        produtoExistente.setPreco(new BigDecimal("10.50"));

        PedidoProduto pedidoProduto = new PedidoProduto();
        pedidoProduto.setProduto(new Produto());
        pedidoProduto.getProduto().setId(1);
        pedidoProduto.setQuantidade(2);

        Set<PedidoProduto> produtosDoPedido = new HashSet<>();
        produtosDoPedido.add(pedidoProduto);

        pedidoParaSalvar = new Pedido();
        pedidoParaSalvar.setPedidoProdutos(produtosDoPedido);
    }

    @Test
    void deveCriarPedidoComSucesso_CalculandoTotalEStatusCorretamente() {
        when(buscarProdutoPeloIdUseCase.execute(1)).thenReturn(produtoExistente);
        when(salvarPedido.execute(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pedido pedidoCriado = criarPedidoUseCase.execute(pedidoParaSalvar);

        assertNotNull(pedidoCriado);
        assertEquals(PedidoStatus.AGUARDANDO_PAGAMENTO, pedidoCriado.getStatus());

        assertEquals(0, new BigDecimal("21.00").compareTo(pedidoCriado.getTotal()));

        PedidoProduto produtoNoPedidoFinal = pedidoCriado.getPedidoProdutos().iterator().next();
        assertEquals(0, produtoExistente.getPreco().compareTo(produtoNoPedidoFinal.getValorUnitarioProdutoMomentoVenda()));

        verify(buscarProdutoPeloIdUseCase, times(1)).execute(1);

        verify(salvarPedido, times(1)).execute(any(Pedido.class));
    }
}