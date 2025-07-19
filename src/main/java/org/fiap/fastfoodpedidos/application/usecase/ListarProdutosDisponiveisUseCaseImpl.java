package org.fiap.fastfoodpedidos.application.usecase;

import lombok.RequiredArgsConstructor;
import org.fiap.fastfoodpedidos.application.port.driven.BuscarProdutos;
import org.fiap.fastfoodpedidos.application.port.driver.ListarProdutosDisponiveisUseCase;
import org.fiap.fastfoodpedidos.domain.model.Produto;
import java.util.List;

@RequiredArgsConstructor
public class ListarProdutosDisponiveisUseCaseImpl implements ListarProdutosDisponiveisUseCase {

    private final BuscarProdutos buscarProdutos;

    @Override
    public List<Produto> executar() {
        return buscarProdutos.buscarTodos();
    }
}