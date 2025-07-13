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
    public SolicitarPagamento solicitarPagamento(RestTemplate restTemplate) {
        return new PagamentoClientAdapter(restTemplate);
    }

    @Bean
    public IniciarPagamentoUseCase iniciarPagamentoUseCase(final SolicitarPagamento solicitarPagamento, final ManipularPagamento manipularPagamento, final ConsultarPedido consultarPedido) {
        return new IniciarPagamentoUseCaseUseCaseImpl(solicitarPagamento, manipularPagamento, consultarPedido);
    }

    @Bean
    public ConfirmarPagamentoUseCase confirmarPagamentoUseCase(final ManipularPagamento manipularPagamento, final AtualizarStatusPedidoUseCase atualizarStatusPedidoUseCase) {
        return new ConfirmarPagamentoUseCaseUseCaseImpl(manipularPagamento, atualizarStatusPedidoUseCase);
    }

    @Bean
    public CancelarPagamentoUseCase cancelarPagamentoUseCase(final ManipularPagamento manipularPagamento, final AtualizarStatusPedidoUseCase atualizarStatusPedidoUseCase) {
        return new CancelarPagamentoUseCaseUseCaseImpl(manipularPagamento, atualizarStatusPedidoUseCase);
    }
}