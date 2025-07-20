package org.fiap.fastfoodpedidos.infrastructure.adapter.rest.cliente;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fiap.fastfoodpedidos.AbstractIntegrationTest;
import org.fiap.fastfoodpedidos.application.port.driver.CriarClienteUseCase;
import org.fiap.fastfoodpedidos.domain.model.Cliente;
import org.fiap.fastfoodpedidos.infrastructure.adapter.rest.cliente.dto.RegistrarClienteRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class ClienteRestAdapterTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CriarClienteUseCase criarClienteUseCase;

    @Test
    void deveRegistrarCliente_QuandoEnviarRequisicaoValida() throws Exception {
        RegistrarClienteRequest requestBody = new RegistrarClienteRequest("John Doe", "john.doe@example.com", "34908306010");

        Cliente clienteCriado = new Cliente(1, "John Doe", "john.doe@example.com", "34908306010");

        when(criarClienteUseCase.execute(any(Cliente.class))).thenReturn(clienteCriado);

        mockMvc.perform(post("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isCreated()) // Agora o status 201 será o esperado
                .andExpect(header().string("Location", "/api/v1/clientes/1"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("John Doe")))
                .andExpect(jsonPath("$.cpf", is("34908306010")));
    }

    @Test
    void naoDeveRegistrarCliente_QuandoNomeEstaVazio_RetornarBadRequest() throws Exception {
        RegistrarClienteRequest requestBodyInvalido = new RegistrarClienteRequest("", "jane.doe@example.com", "34908306010");

        mockMvc.perform(post("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBodyInvalido)))
                .andExpect(status().isBadRequest()); // Esperamos 400 Bad Request
    }
}