package org.fiap.fastfoodpedidos.infrastructure.adapter.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fiap.fastfoodpedidos.domain.model.Produto;
import org.fiap.fastfoodpedidos.infrastructure.config.RestTemplateConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(ProdutoClientAdapter.class)
@Import(RestTemplateConfig.class)
class ProdutoClientAdapterTest {

    @Autowired
    private ProdutoClientAdapter produtoClientAdapter;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Autowired
    private ObjectMapper objectMapper;

    private String baseUrl = "http://localhost:8081/api/v1/produtos";

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.bindTo(restTemplate).build();
    }

    @Test
    void deveBuscarProdutoPorIdComSucesso() throws Exception {
        int produtoId = 1;
        Produto produtoEsperado = new Produto(produtoId, "Hambúrguer", "Delicioso", new BigDecimal("25.00"), null);
        String respostaJson = objectMapper.writeValueAsString(produtoEsperado);

        mockServer.expect(requestTo(baseUrl + "/" + produtoId))
                .andRespond(withSuccess(respostaJson, MediaType.APPLICATION_JSON));

        Produto produtoRetornado = produtoClientAdapter.execute(produtoId);

        assertThat(produtoRetornado).isNotNull();
        assertThat(produtoRetornado.getId()).isEqualTo(produtoId);
        assertThat(produtoRetornado.getNome()).isEqualTo("Hambúrguer");

        mockServer.verify();
    }

    @Test
    void deveBuscarTodosOsProdutosComSucesso() throws Exception {
        Produto produto1 = new Produto(1, "Hambúrguer", "Delicioso", new BigDecimal("25.00"), null);
        Produto produto2 = new Produto(2, "Batata Frita", "Crocante", new BigDecimal("15.00"), null);
        List<Produto> listaEsperada = List.of(produto1, produto2);
        String respostaJson = objectMapper.writeValueAsString(listaEsperada);

        mockServer.expect(requestTo(baseUrl))
                .andRespond(withSuccess(respostaJson, MediaType.APPLICATION_JSON));

        List<Produto> listaRetornada = produtoClientAdapter.buscarTodos();

        assertThat(listaRetornada).isNotNull();
        assertThat(listaRetornada).hasSize(2);

        mockServer.verify();
    }
}