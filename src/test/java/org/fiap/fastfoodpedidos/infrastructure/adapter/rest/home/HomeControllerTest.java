package org.fiap.fastfoodpedidos.infrastructure.adapter.rest.home;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Teste de integração focado na HomeController.
 * Usa @WebMvcTest para carregar apenas a camada web necessária.
 */
@WebMvcTest(HomeController.class)
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveRedirecionarRaizParaSwaggerUI() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/swagger-ui/index.html"));
    }

    @Test
    void deveRedirecionarEndpointRedoc() throws Exception {
        mockMvc.perform(get("/redoc"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/redoc.html"));
    }
}