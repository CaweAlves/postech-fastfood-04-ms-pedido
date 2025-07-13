package org.fiap.fastfoodpedidos.application.port.driven;

import org.fiap.fastfoodpedidos.domain.model.Produto;

public interface BuscarProdutoPeloIdUseCase {

    Produto execute(Integer id);
}
