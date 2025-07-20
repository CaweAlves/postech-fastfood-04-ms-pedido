package org.fiap.fastfoodpedidos.infrastructure.adapter.rest.pagamento;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fiap.fastfoodpedidos.AbstractIntegrationTest;
import org.fiap.fastfoodpedidos.application.port.driver.IniciarPagamentoUseCase;
import org.fiap.fastfoodpedidos.infrastructure.adapter.rest.pagamento.dto.IniciarPagamentoRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class PagamentoRestAdapterTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IniciarPagamentoUseCase iniciarPagamentoUseCase;

    @Test
    void deveIniciarPagamento_eRetornarQrCode_QuandoRequisicaoValida() throws Exception {
        int pedidoId = 1;
        IniciarPagamentoRequest requestBody = new IniciarPagamentoRequest();
        requestBody.setPedidoId(pedidoId);

        byte[] fakeQrCodeBytes = "isto-e-um-qrcode-fake".getBytes();
        Resource qrCodeResource = new ByteArrayResource(fakeQrCodeBytes);

        when(iniciarPagamentoUseCase.execute(pedidoId)).thenReturn(qrCodeResource);

        mockMvc.perform(post("/api/v1/pagamentos/iniciar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk()) // Esperamos 200 OK
                .andExpect(header().string("Content-Type", "image/png"))
                .andExpect(content().bytes(fakeQrCodeBytes));
    }

    @Test
    void naoDeveIniciarPagamento_QuandoPedidoIdEhNulo_RetornarBadRequest() throws Exception {
        IniciarPagamentoRequest requestBodyInvalido = new IniciarPagamentoRequest();

        mockMvc.perform(post("/api/v1/pagamentos/iniciar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBodyInvalido)))
                .andExpect(status().isBadRequest());
    }
}