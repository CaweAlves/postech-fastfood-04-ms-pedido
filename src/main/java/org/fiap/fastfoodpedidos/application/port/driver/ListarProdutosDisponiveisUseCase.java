package org.fiap.fastfoodpedidos.application.port.driver;

import org.fiap.fastfoodpedidos.domain.model.Produto;
import java.util.List;

public interface ListarProdutosDisponiveisUseCase {
    List<Produto> executar();
}