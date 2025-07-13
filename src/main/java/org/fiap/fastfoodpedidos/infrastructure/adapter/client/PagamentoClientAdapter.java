package org.fiap.fastfoodpedidos.infrastructure.adapter.client;

import lombok.RequiredArgsConstructor;
import org.fiap.fastfoodpedidos.application.port.driven.SolicitarPagamento;
import org.fiap.fastfoodpedidos.domain.model.Pagamento;
import org.fiap.fastfoodpedidos.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class PagamentoClientAdapter implements SolicitarPagamento {

    private final RestTemplate restTemplate;

    @Value("${app.services.pagamentos}")
    private String pagamentosServiceUrl;

    @Override
    public Pagamento gerarQRCode(Pedido pedido) {
        try {
            return restTemplate.postForObject(pagamentosServiceUrl, pedido, Pagamento.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao solicitar pagamento para o pedido: " + pedido.getId(), e);
        }
    }
}