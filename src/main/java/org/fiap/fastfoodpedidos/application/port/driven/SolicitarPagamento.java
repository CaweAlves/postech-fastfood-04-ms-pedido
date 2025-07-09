package org.fiap.fastfoodpedidos.application.port.driven;

import org.fiap.fastfoodpedidos.domain.model.Pagamento;
import org.fiap.fastfoodpedidos.domain.model.Pedido;

public interface SolicitarPagamento {

    Pagamento gerarQRCode(Pedido pedido);

}
