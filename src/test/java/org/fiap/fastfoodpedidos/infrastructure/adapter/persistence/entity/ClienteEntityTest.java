package org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Teste unitário para a classe ClienteEntity.
 * Foco: Validar construtores, getters, setters, equals, hashCode e toString.
 */
class ClienteEntityTest {

    @Test
    void deveTestarSettersEGetters() {
        ClienteEntity cliente = new ClienteEntity();

        cliente.setId(1);
        cliente.setNome("John Doe");
        cliente.setEmail("john.doe@example.com");
        cliente.setCpf("12345678901");

        assertThat(cliente.getId()).isEqualTo(1);
        assertThat(cliente.getNome()).isEqualTo("John Doe");
        assertThat(cliente.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(cliente.getCpf()).isEqualTo("12345678901");
    }

    @Test
    void deveTestarMetodosChaining() {
        ClienteEntity cliente = new ClienteEntity()
                .id(1)
                .nome("Jane Doe")
                .email("jane.doe@example.com")
                .cpf("09876543210");

        assertThat(cliente.getId()).isEqualTo(1);
        assertThat(cliente.getNome()).isEqualTo("Jane Doe");
        assertThat(cliente.getEmail()).isEqualTo("jane.doe@example.com");
        assertThat(cliente.getCpf()).isEqualTo("09876543210");
    }

    @Test
    void deveTestarEqualsHashCodeEToString() {
        ClienteEntity cliente1 = new ClienteEntity();
        cliente1.setId(1);

        ClienteEntity cliente2 = new ClienteEntity();
        cliente2.setId(1);

        ClienteEntity cliente3 = new ClienteEntity();
        cliente3.setId(2);

        ClienteEntity cliente4 = new ClienteEntity();

        assertTrue(cliente1.equals(cliente2));
        assertTrue(cliente1.equals(cliente1));
        assertFalse(cliente1.equals(cliente3));
        assertFalse(cliente1.equals(null));
        assertFalse(cliente1.equals(new Object()));
        assertFalse(cliente1.equals(cliente4));
        assertFalse(cliente4.equals(cliente1));

        assertThat(cliente1.hashCode()).isEqualTo(cliente3.hashCode());

        assertThat(cliente1.toString()).contains("id=1", "nome='null'", "email='null'", "cpf='null'");
    }
}