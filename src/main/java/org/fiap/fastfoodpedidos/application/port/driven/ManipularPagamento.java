package org.fiap.fastfoodpedidos.application.port.driven;

import java.util.List;

import org.fiap.fastfoodpedidos.domain.model.Pagamento;

public interface ManipularPagamento {

    Pagamento criarPagamento(Pagamento pagamento);

    Pagamento buscarPagamentoPorIdExterno(String idExterno);

    Pagamento alterarPagamento(Pagamento pagamento);

    List<Pagamento> buscarPagamentosPorIdPedido(Integer idPedido);

}
