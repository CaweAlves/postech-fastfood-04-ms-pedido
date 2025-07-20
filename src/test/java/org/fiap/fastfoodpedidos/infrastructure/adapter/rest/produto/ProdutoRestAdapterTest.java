package org.fiap.fastfoodpedidos.infrastructure.adapter.rest.produto;

import org.fiap.fastfoodpedidos.AbstractIntegrationTest;
import org.fiap.fastfoodpedidos.application.port.driver.ListarProdutosDisponiveisUseCase;
import org.fiap.fastfoodpedidos.domain.model.Produto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class ProdutoRestAdapterTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ListarProdutosDisponiveisUseCase listarProdutosDisponiveisUseCase;

    @Test
    void deveRetornarListaDeProdutosDisponiveis() throws Exception {
        Produto produto1 = new Produto(1, "Hambúrguer", "Delicioso", new BigDecimal("25.00"), null);
        Produto produto2 = new Produto(2, "Batata Frita", "Crocante", new BigDecimal("15.00"), null);
        List<Produto> listaDeProdutos = List.of(produto1, produto2);

        when(listarProdutosDisponiveisUseCase.executar()).thenReturn(listaDeProdutos);

        mockMvc.perform(get("/api/v1/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nome", is("Hambúrguer")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nome", is("Batata Frita")));
    }
}