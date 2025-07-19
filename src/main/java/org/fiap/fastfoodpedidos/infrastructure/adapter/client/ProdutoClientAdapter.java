package org.fiap.fastfoodpedidos.infrastructure.adapter.client;

import lombok.RequiredArgsConstructor;
import org.fiap.fastfoodpedidos.application.port.driven.BuscarProdutoPeloIdUseCase;
import org.fiap.fastfoodpedidos.application.port.driven.BuscarProdutos;
import org.fiap.fastfoodpedidos.domain.model.Produto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProdutoClientAdapter implements BuscarProdutoPeloIdUseCase, BuscarProdutos {

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

    @Override
    public List<Produto> buscarTodos() {
        try {
            Produto[] produtos = restTemplate.getForObject(produtosServiceUrl, Produto[].class);
            return produtos != null ? Arrays.asList(produtos) : Collections.emptyList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar a lista de produtos", e);
        }
    }
}