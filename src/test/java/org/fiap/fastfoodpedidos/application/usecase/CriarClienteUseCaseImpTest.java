package org.fiap.fastfoodpedidos.application.usecase;

import org.fiap.fastfoodpedidos.application.port.driven.SalvarCliente;
import org.fiap.fastfoodpedidos.domain.model.Cliente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarClienteUseCaseImpTest {

    @Mock
    private SalvarCliente salvarCliente;

    @InjectMocks
    private CriarClienteUseCaseImp criarClienteUseCase;

    @Test
    void deveCriarClienteComSucesso() {
        Cliente clienteParaSalvar = new Cliente(null, "Jane Doe", "jane.doe@example.com", "34908306010");
        Cliente clienteSalvoComId = new Cliente(1, "Jane Doe", "jane.doe@example.com", "34908306010");

        when(salvarCliente.execute(any(Cliente.class))).thenReturn(clienteSalvoComId);

        Cliente resultado = criarClienteUseCase.execute(clienteParaSalvar);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Jane Doe", resultado.getNome());

        verify(salvarCliente, times(1)).execute(clienteParaSalvar);
    }
}