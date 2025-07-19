package org.fiap.fastfoodpedidos.infrastructure.config;

import org.fiap.fastfoodpedidos.application.port.driven.*;
import org.fiap.fastfoodpedidos.application.port.driver.*;
import org.fiap.fastfoodpedidos.application.usecase.*;
import org.fiap.fastfoodpedidos.infrastructure.adapter.client.PagamentoClientAdapter;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.PedidoAdapter;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.mapper.PedidoPersistenceMapper;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.repository.PedidoProdutoRepository;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.repository.PedidoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfiguration {

    @Bean
    public PedidoAdapter pedidoAdapter(final PedidoRepository pedidoRepository, final PedidoPersistenceMapper pedidoPersistenceMapper, final PedidoProdutoRepository pedidoProdutoRepository) {
        return new PedidoAdapter(pedidoRepository, pedidoPersistenceMapper, pedidoProdutoRepository);
    }

    @Bean
    public CriarPedidoUseCase criarPedidoUseCase(final SalvarPedido salvarPedido, final BuscarProdutoPeloIdUseCase buscarProdutoPeloIdUseCase) {
        return new CriarPedidoUseCaseImp(salvarPedido, buscarProdutoPeloIdUseCase);
    }

    @Bean
    public BuscarPedidoUseCase buscarPedidoUseCase(final ConsultarPedido consultarPedido) {
        return new BuscarPedidoUseCaseImp(consultarPedido);
    }

    @Bean
    public BuscarPedidosUseCase buscarPedidosUseCase(final ConsultarPedidos consultarPedidos) {
        return new BuscarPedidosUseCaseImp(consultarPedidos);
    }

    @Bean
    public BuscarPedidosEmAndamentoUseCase buscarPedidosEmAndamentoUseCase(final ConsultarPedidosEmAndamento consultarPedidos) {
        return new BuscarPedidosEmAndamentoUseCaseImp(consultarPedidos);
    }

    @Bean
    public AtualizarStatusPedidoUseCase atualizarStatusPedidoUseCase(final ConsultarPedido consultarPedido, final SalvarPedido salvarPedido) {
        return new AtualizarStatusPedidoUseCaseImp(consultarPedido, salvarPedido);
    }

    @Bean
    public IniciarPagamentoUseCase iniciarPagamentoUseCase(final SolicitarPagamento solicitarPagamento, final ConsultarPedido consultarPedido) {
        return new IniciarPagamentoUseCaseUseCaseImpl(solicitarPagamento, consultarPedido);
    }

    @Bean
    public SolicitarPagamento solicitarPagamento(RestTemplate restTemplate) {
        return new PagamentoClientAdapter(restTemplate);
    }

    @Bean
    public ConfirmarPagamentoUseCase confirmarPagamentoUseCase(final AtualizarStatusPedidoUseCase atualizarStatusPedidoUseCase) {
        return new ConfirmarPagamentoUseCaseUseCaseImpl(atualizarStatusPedidoUseCase);
    }

    @Bean
    public CancelarPagamentoUseCase cancelarPagamentoUseCase(final AtualizarStatusPedidoUseCase atualizarStatusPedidoUseCase) {
        return new CancelarPagamentoUseCaseUseCaseImpl(atualizarStatusPedidoUseCase);
    }

    @Bean
    public CriarClienteUseCaseImp criarClienteUseCaseImp(SalvarCliente salvarCliente) {
        return new CriarClienteUseCaseImp(salvarCliente);
    }

    @Bean
    public ListarProdutosDisponiveisUseCase listarProdutosDisponiveisUseCase(final BuscarProdutos buscarProdutos) {
        return new ListarProdutosDisponiveisUseCaseImpl(buscarProdutos);
    }
}