package org.fiap.fastfoodpedidos.infrastructure.config;

import org.fiap.fastfoodpedidos.application.port.driven.*;
import org.fiap.fastfoodpedidos.application.port.driver.*;
import org.fiap.fastfoodpedidos.application.usecase.*;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.*;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.mapper.*;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {


    @Bean
    public PedidoAdapter pedidoAdapter(final PedidoRepository pedidoRepository, final PedidoPersistenceMapper pedidoPersistenceMapper,
                                       final ProdutoPersistenceMapper produtoPersistenceMapper, final PedidoProdutoRepository pedidoProdutoRepository) {
        return new PedidoAdapter(pedidoRepository, pedidoPersistenceMapper, produtoPersistenceMapper, pedidoProdutoRepository);
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
    public SolicitarPagamento solicitarPagamento() {
        return new MercadoPagoIntegrationImpl();
    }

}
