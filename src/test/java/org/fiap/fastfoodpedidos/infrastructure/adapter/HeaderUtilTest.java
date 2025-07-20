package org.fiap.fastfoodpedidos.infrastructure.adapter;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Teste unitário para a classe de utilidade HeaderUtil.
 */
class HeaderUtilTest {

    private final String applicationName = "test-app";

    @Test
    void deveCriarAlert() {
        String message = "Test message";
        String param = "test-param";

        HttpHeaders headers = HeaderUtil.createAlert(applicationName, message, param);

        assertThat(headers.getFirst("X-test-app-alert")).isEqualTo(message);
        assertThat(headers.getFirst("X-test-app-params")).isEqualTo("test-param");
    }

    @Test
    void deveCriarEntityCreationAlert() {
        String entityName = "Pedido";
        String param = "123";

        HttpHeaders headers = HeaderUtil.createEntityCreationAlert(applicationName, true, entityName, param);

        assertThat(headers.getFirst("X-test-app-alert")).isEqualTo("test-app.Pedido.created");
        assertThat(headers.getFirst("X-test-app-params")).isEqualTo(param);
    }

    @Test
    void deveCriarEntityUpdateAlert() {
        String entityName = "Cliente";
        String param = "456";

        HttpHeaders headers = HeaderUtil.createEntityUpdateAlert(applicationName, true, entityName, param);

        assertThat(headers.getFirst("X-test-app-alert")).isEqualTo("test-app.Cliente.updated");
        assertThat(headers.getFirst("X-test-app-params")).isEqualTo(param);
    }

    @Test
    void deveCriarEntityDeletionAlert() {
        String entityName = "Produto";
        String param = "789";

        HttpHeaders headers = HeaderUtil.createEntityDeletionAlert(applicationName, true, entityName, param);

        assertThat(headers.getFirst("X-test-app-alert")).isEqualTo("test-app.Produto.deleted");
        assertThat(headers.getFirst("X-test-app-params")).isEqualTo(param);
    }

    @Test
    void deveCriarFailureAlert() {
        String entityName = "Pagamento";
        String errorKey = "pagamento-falhou";
        String defaultMessage = "O pagamento falhou";

        HttpHeaders headers = HeaderUtil.createFailureAlert(applicationName, true, entityName, errorKey, defaultMessage);

        assertThat(headers.getFirst("X-test-app-error")).isEqualTo("error.pagamento-falhou");
        assertThat(headers.getFirst("X-test-app-params")).isEqualTo(entityName);
    }
}