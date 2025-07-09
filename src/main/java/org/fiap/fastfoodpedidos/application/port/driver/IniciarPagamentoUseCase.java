package org.fiap.fastfoodpedidos.application.port.driver;

import org.fiap.fastfoodpedidos.domain.model.Pagamento;

public interface IniciarPagamentoUseCase {

    Pagamento execute(Integer idPedido);

}
