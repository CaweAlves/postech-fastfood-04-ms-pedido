package org.fiap.fastfoodpedidos.application.usecase;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fiap.fastfoodpedidos.application.port.driven.ConsultarPedido;
import org.fiap.fastfoodpedidos.application.port.driven.ManipularPagamento;
import org.fiap.fastfoodpedidos.application.port.driven.SolicitarPagamento;
import org.fiap.fastfoodpedidos.application.port.driver.IniciarPagamentoUseCase;
import org.fiap.fastfoodpedidos.domain.enumeration.PagamentoStatus;
import org.fiap.fastfoodpedidos.domain.enumeration.PedidoStatus;
import org.fiap.fastfoodpedidos.domain.exception.PedidoNaoAplicavelParaPagamentoException;
import org.fiap.fastfoodpedidos.domain.model.Pagamento;
import org.fiap.fastfoodpedidos.domain.model.Pedido;

import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class IniciarPagamentoUseCaseUseCaseImpl implements IniciarPagamentoUseCase {

    private final SolicitarPagamento solicitarPagamento;
    private final ManipularPagamento manipularPagamento;
    private final ConsultarPedido consultarPedido;


    @Override
    public Pagamento execute(Integer idPedido) {
        Pedido pedido = this.consultarPedido.execute(idPedido);

        if (!pedido.getStatus().equals(PedidoStatus.AGUARDANDO_PAGAMENTO)) {
        	throw new PedidoNaoAplicavelParaPagamentoException();
        }

        List<Pagamento> listaPagamentos = this.manipularPagamento.buscarPagamentosPorIdPedido(idPedido);
        if (listaPagamentos == null || listaPagamentos.isEmpty()) {
            return this.salvarPagamento(pedido);
        } else {

        	Optional<Pagamento> pagamentoRealizado = listaPagamentos.stream()
        												.filter(pagamento -> pagamento.getStatus() == PagamentoStatus.REALIZADO)
        												.findFirst();
        	
        	Optional<Pagamento> pagamentoPendente = listaPagamentos.stream()
														.filter(pagamento -> pagamento.getStatus() == PagamentoStatus.PENDENTE)
														.findFirst();
        	
        	Optional<Pagamento> pagamentoCancelado = listaPagamentos.stream()
														.filter(pagamento -> pagamento.getStatus() == PagamentoStatus.CANCELADO)
														.findFirst();
        	
        	if (pagamentoRealizado.isEmpty() && pagamentoPendente.isEmpty()) {
            	return this.salvarPagamento(pedido);
        	}
        	if (pagamentoCancelado.isPresent() && pagamentoRealizado.isEmpty()) {
        		return this.salvarPagamento(pedido);
        	}
        	
        	throw new PedidoNaoAplicavelParaPagamentoException();
        }
    }
    
    private Pagamento salvarPagamento(Pedido pedido) {
    	Pagamento pagamento = this.solicitarPagamento.gerarQRCode(pedido);
        pagamento.setStatus(PagamentoStatus.PENDENTE);
        pagamento = this.manipularPagamento.criarPagamento(pagamento);
        log.info("Pagamento {} salvo", pagamento.getId());
        return pagamento;
    }
    
}

