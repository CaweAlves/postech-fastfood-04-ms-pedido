package org.fiap.fastfoodpedidos.application.port.driver;

import org.fiap.fastfoodpedidos.domain.model.Pagamento;

public interface CancelarPagamentoUseCase {

    Pagamento execute(String idExternoPagamento);

}
