package org.fiap.fastfoodpedidos.infrastructure.adapter.rest.pedido;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fiap.fastfoodpedidos.AbstractIntegrationTest;
import org.fiap.fastfoodpedidos.application.port.driver.BuscarPedidoUseCase;
import org.fiap.fastfoodpedidos.application.port.driver.BuscarPedidosEmAndamentoUseCase;
import org.fiap.fastfoodpedidos.application.port.driver.BuscarPedidosUseCase;
import org.fiap.fastfoodpedidos.application.port.driver.CriarPedidoUseCase;
import org.fiap.fastfoodpedidos.domain.enumeration.PedidoStatus;
import org.fiap.fastfoodpedidos.domain.model.Pedido;
import org.fiap.fastfoodpedidos.domain.model.PedidoProduto;
import org.fiap.fastfoodpedidos.domain.model.Produto;
import org.fiap.fastfoodpedidos.infrastructure.adapter.rest.pedido.dto.ProdutoQuantidadePedidoRequest;
import org.fiap.fastfoodpedidos.infrastructure.adapter.rest.pedido.dto.RegistrarPedidoRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class PedidoRestAdapterTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CriarPedidoUseCase criarPedidoUseCase;
    @MockBean
    private BuscarPedidoUseCase buscarPedidoUseCase;
    @MockBean
    private BuscarPedidosUseCase buscarPedidosUseCase;
    @MockBean
    private BuscarPedidosEmAndamentoUseCase buscarPedidosEmAndamentoUseCase;

    @Test
    void deveCriarPedido_QuandoEnviarRequisicaoValida_RetornarStatus201Created() throws Exception {
        ProdutoQuantidadePedidoRequest produtoRequest = new ProdutoQuantidadePedidoRequest(1, 2);
        RegistrarPedidoRequest requestBody = new RegistrarPedidoRequest(null, Set.of(produtoRequest));

        Pedido pedidoCriado = new Pedido();
        pedidoCriado.setId(123);
        pedidoCriado.setStatus(PedidoStatus.AGUARDANDO_PAGAMENTO);
        pedidoCriado.setTotal(new BigDecimal("50.00"));

        when(criarPedidoUseCase.execute(any(Pedido.class))).thenReturn(pedidoCriado);

        mockMvc.perform(post("/api/v1/pedidos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody))).andExpect(status().isCreated());
    }

    @Test
    void naoDeveCriarPedido_QuandoEnviarRequisicaoInvalida_RetornarStatus400BadRequest() throws Exception {
        RegistrarPedidoRequest requestBodyInvalido = new RegistrarPedidoRequest(1, null);

        mockMvc.perform(post("/api/v1/pedidos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBodyInvalido))).andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornarPedido_QuandoBuscarPorIdExistente() throws Exception {
        int pedidoId = 1;
        Produto produto = Produto.builder().id(10).nome("Hambúrguer").build();
        PedidoProduto pedidoProduto = PedidoProduto.builder().produto(produto).quantidade(1).build();
        Pedido pedido = Pedido.builder()
                .id(pedidoId)
                .status(PedidoStatus.RECEBIDO)
                .total(new BigDecimal("25.50"))
                .pedidoProdutos(Set.of(pedidoProduto))
                .build();

        when(buscarPedidoUseCase.execute(pedidoId)).thenReturn(pedido);

        mockMvc.perform(get("/api/v1/pedidos/{id}", pedidoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(pedidoId)))
                .andExpect(jsonPath("$.status", is("RECEBIDO")))
                .andExpect(jsonPath("$.total", is(25.50)))
                .andExpect(jsonPath("$.pedidoProdutos", hasSize(1)));
    }

    @Test
    void deveRetornarListaDeTodosPedidos() throws Exception {
        // Arrange (Given)
        Pedido pedido1 = Pedido.builder().id(1).build();
        Pedido pedido2 = Pedido.builder().id(2).build();
        List<Pedido> todosPedidos = List.of(pedido1, pedido2);

        when(buscarPedidosUseCase.execute()).thenReturn(todosPedidos);

        mockMvc.perform(get("/api/v1/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // Verifica se a lista na raiz do JSON tem 2 elementos
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    void deveRetornarApenasPedidosEmAndamento() throws Exception {
        Pedido pedidoEmAndamento = Pedido.builder()
                .id(1)
                .status(PedidoStatus.EM_PREPARACAO)
                .dataHoraPedidoRecebido(java.time.LocalDateTime.now().minusMinutes(5)) // Para testar o tempo de espera
                .build();
        List<Pedido> pedidos = List.of(pedidoEmAndamento);

        when(buscarPedidosEmAndamentoUseCase.execute()).thenReturn(pedidos);

        mockMvc.perform(get("/api/v1/pedidos/em-andamento"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].status", is("EM_PREPARACAO")))
                .andExpect(jsonPath("$[0].tempoDeEspera").exists()); // Verifica se o campo calculado existe
    }
}