package org.fiap.fastfoodpedidos.infrastructure.adapter.persistence;

import org.fiap.fastfoodpedidos.domain.model.Cliente;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.ClienteEntity;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.mapper.ClientePersistenceMapper;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({ClienteAdapter.class, ClienteAdapterTest.MapperTestConfig.class})
class ClienteAdapterTest {

    @TestConfiguration
    static class MapperTestConfig {
        @Bean
        public ClientePersistenceMapper clientePersistenceMapper() {
            return Mappers.getMapper(ClientePersistenceMapper.class);
        }
    }

    @Autowired
    private ClienteAdapter clienteAdapter;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void deveSalvarUmClienteComSucesso() {
        Cliente clienteParaSalvar = Cliente.builder()
                .nome("Test User")
                .email("test@user.com")
                .cpf("11122233344")
                .build();

        Cliente clienteSalvo = clienteAdapter.execute(clienteParaSalvar);

        assertThat(clienteSalvo).isNotNull();
        assertThat(clienteSalvo.getId()).isNotNull();
        assertThat(clienteSalvo.getNome()).isEqualTo("Test User");

        Optional<ClienteEntity> clienteEntityOptional = clienteRepository.findById(clienteSalvo.getId());
        assertThat(clienteEntityOptional).isPresent();
        ClienteEntity clienteEntity = clienteEntityOptional.get();
        assertThat(clienteEntity.getCpf()).isEqualTo("11122233344");
    }
}