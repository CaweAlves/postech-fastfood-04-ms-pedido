package org.fiap.fastfoodpedidos.infrastructure.adapter.client;

import lombok.RequiredArgsConstructor;
import org.fiap.fastfoodpedidos.application.port.driven.BuscarProdutoPeloIdUseCase;
import org.fiap.fastfoodpedidos.domain.model.Produto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ProdutoClientAdapter implements BuscarProdutoPeloIdUseCase {

    private final RestTemplate restTemplate;

    @Value("${app.services.produtos}")
    private String produtosServiceUrl;

    @Override
    public Produto execute(Integer id) {
        String url = produtosServiceUrl + "/" + id;
        try {
            return restTemplate.getForObject(url, Produto.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar produto com ID: " + id, e);
        }
    }
}