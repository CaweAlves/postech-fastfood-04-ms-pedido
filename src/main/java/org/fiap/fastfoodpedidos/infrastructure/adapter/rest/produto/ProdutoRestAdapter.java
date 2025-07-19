package org.fiap.fastfoodpedidos.infrastructure.adapter.rest.produto;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.fiap.fastfoodpedidos.application.port.driver.ListarProdutosDisponiveisUseCase;
import org.fiap.fastfoodpedidos.domain.model.Produto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/produtos") // O endpoint base agora é /produtos
@RequiredArgsConstructor
public class ProdutoRestAdapter {

    private final ListarProdutosDisponiveisUseCase listarProdutosDisponiveisUseCase;

    @GetMapping
    @Operation(summary = "Lista os produtos disponíveis no cardápio")
    public ResponseEntity<List<Produto>> getProdutosDisponiveis() {
        List<Produto> produtos = listarProdutosDisponiveisUseCase.executar();
        return ResponseEntity.ok(produtos);
    }
}