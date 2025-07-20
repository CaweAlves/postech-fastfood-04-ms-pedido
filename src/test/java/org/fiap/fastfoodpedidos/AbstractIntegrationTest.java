package org.fiap.fastfoodpedidos;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Classe base abstrata para todos os testes de integração.
 * Inicia os contentores Docker (PostgreSQL e LocalStack) uma vez para todos os testes
 * que herdarem dela, e configura dinamicamente as propriedades da aplicação.
 * Esta implementação usa o padrão Singleton Container para garantir que os contentores
 * sejam partilhados entre diferentes classes de teste.
 */
@SpringBootTest
@ActiveProfiles("test")
public abstract class AbstractIntegrationTest {

    static final PostgreSQLContainer<?> postgreSQLContainer;
    static final LocalStackContainer localStackContainer;

    static {
        postgreSQLContainer = new PostgreSQLContainer<>("postgres:14")
                .withDatabaseName("testdb")
                .withUsername("sa")
                .withPassword("password");

        localStackContainer = new LocalStackContainer(DockerImageName.parse("localstack/localstack:latest-amd64"))
                .withServices(LocalStackContainer.Service.SQS);

        postgreSQLContainer.start();
        localStackContainer.start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);

        registry.add("spring.cloud.aws.endpoint", () -> localStackContainer.getEndpointOverride(LocalStackContainer.Service.SQS).toString());
        registry.add("spring.cloud.aws.credentials.access-key", localStackContainer::getAccessKey);
        registry.add("spring.cloud.aws.credentials.secret-key", localStackContainer::getSecretKey);
        registry.add("spring.cloud.aws.region.static", localStackContainer::getRegion);
    }
}