package org.fiap.fastfoodpedidos.infrastructure.adapter.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fiap.fastfoodpedidos.domain.model.Pedido;
import org.fiap.fastfoodpedidos.infrastructure.config.RestTemplateConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Teste de Integração para o PagamentoClientAdapter.
 * Foco: Validar a comunicação HTTP com o microsserviço de pagamentos.
 */
@RestClientTest(PagamentoClientAdapter.class)
@Import(RestTemplateConfig.class)
class PagamentoClientAdapterTest {

    @Autowired
    private PagamentoClientAdapter pagamentoClientAdapter;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Autowired
    private ObjectMapper objectMapper;

    private String baseUrl = "http://localhost:8082/api/v1/pagamentos";

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.bindTo(restTemplate).build();
    }

    @Test
    void deveGerarQrCodeComSucesso() throws Exception {
        Pedido pedido = Pedido.builder()
                .id(1)
                .total(new BigDecimal("99.99"))
                .build();

        byte[] fakeQrCodeBytes = "qr-code-image-bytes".getBytes();

        mockServer.expect(requestTo(baseUrl + "/iniciar"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(fakeQrCodeBytes, MediaType.IMAGE_PNG));

        Resource qrCodeResource = pagamentoClientAdapter.gerarQRCode(pedido);

        assertThat(qrCodeResource).isNotNull();
        assertThat(qrCodeResource.getContentAsByteArray()).isEqualTo(fakeQrCodeBytes);

        mockServer.verify();
    }
}