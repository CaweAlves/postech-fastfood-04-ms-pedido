package org.fiap.fastfoodpedidos.infrastructure.adapter.rest.pedido.mapper;

import org.fiap.fastfoodpedidos.domain.enumeration.PedidoStatus;
import org.fiap.fastfoodpedidos.domain.model.Cliente;
import org.fiap.fastfoodpedidos.domain.model.Pedido;
import org.fiap.fastfoodpedidos.infrastructure.adapter.rest.pedido.dto.*;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Teste unitário para a classe PedidoRestMapper.
 * Foco: Validar a lógica de mapeamento entre os DTOs da camada REST e o modelo de domínio.
 */
class PedidoRestMapperTest {

    private final PedidoRestMapper mapper = Mappers.getMapper(PedidoRestMapper.class);

    @Test
    void deveMapearRegistrarPedidoRequestParaPedido() {
        ProdutoQuantidadePedidoRequest produtoRequest = new ProdutoQuantidadePedidoRequest(1, 2);
        RegistrarPedidoRequest request = new RegistrarPedidoRequest(10, Set.of(produtoRequest));

        Pedido pedido = mapper.toPedido(request);

        assertThat(pedido).isNotNull();
        assertThat(pedido.getCliente()).isNotNull();
        assertThat(pedido.getCliente().getId()).isEqualTo(10);
        assertThat(pedido.getPedidoProdutos()).hasSize(1);
        assertThat(pedido.getPedidoProdutos().iterator().next().getProduto().getId()).isEqualTo(1);
        assertThat(pedido.getPedidoProdutos().iterator().next().getQuantidade()).isEqualTo(2);
    }

    @Test
    void deveMapearPedidoParaRegistrarPedidoResponse() {
        Pedido pedido = Pedido.builder()
                .id(123)
                .status(PedidoStatus.AGUARDANDO_PAGAMENTO)
                .total(new BigDecimal("50.00"))
                .build();

        RegistrarPedidoResponse response = mapper.toRegistrarPedidoResponse(pedido);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(123);
        assertThat(response.status()).isEqualTo(PedidoStatus.AGUARDANDO_PAGAMENTO);
        assertThat(response.total()).isEqualByComparingTo("50.00");
    }

    @Test
    void deveMapearPedidoParaDetalhePedidoResponse() {
        Cliente cliente = Cliente.builder().id(10).nome("John Doe").build();
        Pedido pedido = Pedido.builder()
                .id(123)
                .status(PedidoStatus.RECEBIDO)
                .total(new BigDecimal("50.00"))
                .cliente(cliente)
                .build();

        DetalhePedidoResponse response = mapper.toDetalhePedidoResponse(pedido);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(123);
        assertThat(response.status()).isEqualTo(PedidoStatus.RECEBIDO);
        assertThat(response.cliente().getId()).isEqualTo(10);
    }

    @Test
    void deveMapearPedidoParaDetalhePedidoEmPreparacaoResponse_eCalcularTempoDeEspera() {
        LocalDateTime dataRecebimento = LocalDateTime.now().minusHours(1).minusMinutes(15);
        Pedido pedido = Pedido.builder()
                .id(123)
                .status(PedidoStatus.EM_PREPARACAO)
                .dataHoraPedidoRecebido(dataRecebimento)
                .build();

        DetalhePedidoEmPreparacaoResponse response = mapper.toDetalhePedidoEmPreparacaoResponse(pedido);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(123);
        assertThat(response.status()).isEqualTo(PedidoStatus.EM_PREPARACAO);
        assertThat(response.tempoDeEspera()).matches("\\d{2}:\\d{2}");
    }
}