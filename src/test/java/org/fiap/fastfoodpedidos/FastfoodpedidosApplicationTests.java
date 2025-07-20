package org.fiap.fastfoodpedidos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
class FastfoodpedidosApplicationTests {

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14").withDatabaseName("testdb").withUsername("sa").withPassword("password");

    @Container
    static LocalStackContainer localStackContainer = new LocalStackContainer(DockerImageName.parse("localstack/localstack:latest-amd64")).withServices(LocalStackContainer.Service.SQS);

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

    @Test
    void contextLoads() {
    }
}