package org.fiap.fastfoodpedidos.application.port.driver;

import org.fiap.fastfoodpedidos.domain.model.Pagamento;
import org.springframework.core.io.Resource;

public interface IniciarPagamentoUseCase {

    Resource execute(Integer idPedido);

}
