package org.fiap.fastfoodpedidos.application.port.driven;

import org.fiap.fastfoodpedidos.domain.model.Pagamento;
import org.fiap.fastfoodpedidos.domain.model.Pedido;
import org.springframework.core.io.Resource;

public interface SolicitarPagamento {

    Resource gerarQRCode(Pedido pedido);

}
