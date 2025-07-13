package org.fiap.fastfoodpedidos.infrastructure.config;

import org.fiap.fastfoodpedidos.application.port.driven.*;
import org.fiap.fastfoodpedidos.application.port.driver.BuscarPedidosEmAndamentoUseCase;
import org.fiap.fastfoodpedidos.application.port.driver.BuscarPedidosUseCase;
import org.fiap.fastfoodpedidos.application.usecase.BuscarPedidoUseCaseImp;
import org.fiap.fastfoodpedidos.application.usecase.BuscarPedidosEmAndamentoUseCaseImp;
import org.fiap.fastfoodpedidos.application.usecase.BuscarPedidosUseCaseImp;
import org.fiap.fastfoodpedidos.application.usecase.CriarPedidoUseCaseImp;
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
    public CriarPedidoUseCaseImp criarPedidoImp(final SalvarPedido salvarPedido, final BuscarProdutoPeloIdUseCase buscarProdutoPeloIdUseCase) {
        return new CriarPedidoUseCaseImp(salvarPedido, buscarProdutoPeloIdUseCase);
    }

    @Bean
    public BuscarPedidoUseCaseImp buscarPedidoImp(final ConsultarPedido consultarPedido) {
        return new BuscarPedidoUseCaseImp(consultarPedido);
    }

    @Bean
    public BuscarPedidosUseCase criarBuscarPedidosUseCase(final ConsultarPedidos consultarPedidos) {
        return new BuscarPedidosUseCaseImp(consultarPedidos);
    }

    @Bean
    public BuscarPedidosEmAndamentoUseCase criarBuscarPedidosEmAndamentoUseCase(final ConsultarPedidosEmAndamento consultarPedidos) {
        return new BuscarPedidosEmAndamentoUseCaseImp(consultarPedidos);
    }

    @Bean
    public SolicitarPagamento solicitarPagamento(RestTemplate restTemplate) {
        return new PagamentoClientAdapter(restTemplate);
    }

}
