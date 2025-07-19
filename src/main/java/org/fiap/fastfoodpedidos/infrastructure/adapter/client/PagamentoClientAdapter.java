package org.fiap.fastfoodpedidos.infrastructure.adapter.client;

import lombok.RequiredArgsConstructor;
import org.fiap.fastfoodpedidos.application.port.driven.SolicitarPagamento;
import org.fiap.fastfoodpedidos.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class PagamentoClientAdapter implements SolicitarPagamento {

    private final RestTemplate restTemplate;

    @Value("${app.services.pagamentos}")
    private String pagamentosServiceUrl;

    @Override
    public Resource gerarQRCode(Pedido pedido) {
        String url = pagamentosServiceUrl + "/iniciar";
        try {
            // Configura os headers da requisição
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM)); // Esperamos uma imagem
            HttpEntity<Pedido> entity = new HttpEntity<>(pedido, headers);

            ResponseEntity<Resource> response = restTemplate.exchange(url, HttpMethod.POST, entity, Resource.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                // Lidar com respostas de erro do serviço de pagamentos
                throw new RuntimeException("Falha ao gerar QR Code. Status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao solicitar pagamento para o pedido: " + pedido.getId(), e);
        }
    }
}