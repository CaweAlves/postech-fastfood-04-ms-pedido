package org.fiap.fastfoodpedidos.infrastructure.adapter.rest.exception;

import org.fiap.fastfoodpedidos.domain.exception.EmailAlreadyUsedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Teste unitário focado no ExceptionTranslator, sem carregar o contexto do Spring.
 */
class ExceptionTranslatorTest {

    private MockMvc mockMvc;

    @RestController
    static class TestController {
        @GetMapping("/test/email-already-used")
        public void throwEmailAlreadyUsed() {
            throw new EmailAlreadyUsedException();
        }

        @GetMapping("/test/concurrency-failure")
        public void throwConcurrencyFailure() {
            throw new org.springframework.dao.ConcurrencyFailureException("Test concurrency failure");
        }
    }

    @BeforeEach
    void setup() {
        MockEnvironment mockEnvironment = new MockEnvironment();
        mockEnvironment.setProperty("app.name", "test-app");

        ExceptionTranslator exceptionTranslator = new ExceptionTranslator(mockEnvironment);

        mockMvc = MockMvcBuilders.standaloneSetup(new TestController())
                .setControllerAdvice(exceptionTranslator)
                .build();
    }

    @Test
    void deveTratarEmailAlreadyUsedException_eRetornarBadRequest() throws Exception {
        mockMvc.perform(get("/test/email-already-used"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title", is("Email is already in use!")))
                .andExpect(jsonPath("$.parameters.message", is("error.emailexists")))
                .andExpect(jsonPath("$.parameters.params", is("userManagement")));
    }

    @Test
    void deveTratarConcurrencyFailureException_eRetornarConflict() throws Exception {
        mockMvc.perform(get("/test/concurrency-failure"))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.parameters.message", is(ErrorConstants.ERR_CONCURRENCY_FAILURE)));
    }
}