package org.fiap.fastfoodpedidos.application.port.driven;

import org.fiap.fastfoodpedidos.domain.model.Pagamento;

public interface ConsultarPagamento {

    Pagamento execute(Integer id);
}
