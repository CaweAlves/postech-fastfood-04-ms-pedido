package org.fiap.fastfoodpedidos.infrastructure.adapter.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fiap.fastfoodpedidos.AbstractIntegrationTest;
import org.fiap.fastfoodpedidos.domain.enumeration.PagamentoStatus;
import org.fiap.fastfoodpedidos.domain.enumeration.PedidoStatus;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.PedidoEntity;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.math.BigDecimal;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

class PagamentoSqsListenerTest extends AbstractIntegrationTest {

    @Autowired
    private SqsAsyncClient sqsAsyncClient;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private String queueUrl;
    private final String NOME_FILA = "fila-pagamentos";

    @BeforeEach
    void setup() {
        queueUrl = sqsAsyncClient.createQueue(CreateQueueRequest.builder().queueName(NOME_FILA).build())
                .join().queueUrl();
    }

    @Test
    void deveAtualizarStatusDoPedidoParaRecebido_QuandoReceberMensagemDeConfirmacao() throws Exception {
        PedidoEntity pedidoSalvo = criarPedidoNoBanco(PedidoStatus.AGUARDANDO_PAGAMENTO);
        ResultadoPagamentoDTO mensagemDTO = new ResultadoPagamentoDTO(pedidoSalvo.getId(), PagamentoStatus.REALIZADO);
        String corpoDaMensagem = objectMapper.writeValueAsString(mensagemDTO);

        sqsAsyncClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(corpoDaMensagem)
                .build());

        await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
            PedidoEntity pedidoAtualizado = pedidoRepository.findById(pedidoSalvo.getId()).orElseThrow();
            assertThat(pedidoAtualizado.getStatus()).isEqualTo(PedidoStatus.RECEBIDO);
        });
    }

    @Test
    void deveAtualizarStatusDoPedidoParaCancelado_QuandoReceberMensagemDeCancelamento() throws Exception {
        PedidoEntity pedidoSalvo = criarPedidoNoBanco(PedidoStatus.AGUARDANDO_PAGAMENTO);
        ResultadoPagamentoDTO mensagemDTO = new ResultadoPagamentoDTO(pedidoSalvo.getId(), PagamentoStatus.CANCELADO);
        String corpoDaMensagem = objectMapper.writeValueAsString(mensagemDTO);

        sqsAsyncClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(corpoDaMensagem)
                .build());

        await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
            PedidoEntity pedidoAtualizado = pedidoRepository.findById(pedidoSalvo.getId()).orElseThrow();
            assertThat(pedidoAtualizado.getStatus()).isEqualTo(PedidoStatus.CANCELADO);
        });
    }

    @Test
    void deveIgnorarMensagem_QuandoStatusNaoForSuportado() throws Exception {
        PedidoEntity pedidoSalvo = criarPedidoNoBanco(PedidoStatus.AGUARDANDO_PAGAMENTO);
        ResultadoPagamentoDTO mensagemDTO = new ResultadoPagamentoDTO(pedidoSalvo.getId(), PagamentoStatus.PENDENTE);
        String corpoDaMensagem = objectMapper.writeValueAsString(mensagemDTO);

        sqsAsyncClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(corpoDaMensagem)
                .build());

        Thread.sleep(2000);
        PedidoEntity pedidoAtual = pedidoRepository.findById(pedidoSalvo.getId()).orElseThrow();
        assertThat(pedidoAtual.getStatus()).isEqualTo(PedidoStatus.AGUARDANDO_PAGAMENTO);
    }

    @Test
    void deveTratarJsonInvalido_eNaoMudarStatusDoPedido() throws Exception {
        PedidoEntity pedidoSalvo = criarPedidoNoBanco(PedidoStatus.AGUARDANDO_PAGAMENTO);
        String corpoDaMensagem = "{\"json_invalido\": ";

        sqsAsyncClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(corpoDaMensagem)
                .build());

        Thread.sleep(2000);
        PedidoEntity pedidoAtual = pedidoRepository.findById(pedidoSalvo.getId()).orElseThrow();
        assertThat(pedidoAtual.getStatus()).isEqualTo(PedidoStatus.AGUARDANDO_PAGAMENTO);
    }

    @Test
    void deveIgnorarMensagem_QuandoPayloadEhInvalido() throws Exception {
        PedidoEntity pedidoSalvo = criarPedidoNoBanco(PedidoStatus.AGUARDANDO_PAGAMENTO);
        String corpoDaMensagem = "{\"pedidoId\": " + pedidoSalvo.getId() + "}";

        sqsAsyncClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(corpoDaMensagem)
                .build());

        Thread.sleep(2000);
        PedidoEntity pedidoAtual = pedidoRepository.findById(pedidoSalvo.getId()).orElseThrow();
        assertThat(pedidoAtual.getStatus()).isEqualTo(PedidoStatus.AGUARDANDO_PAGAMENTO);
    }

    private PedidoEntity criarPedidoNoBanco(PedidoStatus statusInicial) {
        PedidoEntity pedido = new PedidoEntity();
        pedido.setStatus(statusInicial);
        pedido.setTotal(BigDecimal.TEN);
        return pedidoRepository.saveAndFlush(pedido);
    }
}