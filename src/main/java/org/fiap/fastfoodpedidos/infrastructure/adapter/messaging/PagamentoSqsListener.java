package org.fiap.fastfoodpedidos.infrastructure.adapter.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fiap.fastfoodpedidos.application.port.driver.ConfirmarPagamentoUseCase;
import org.fiap.fastfoodpedidos.domain.model.Pagamento;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PagamentoSqsListener {

    private final ConfirmarPagamentoUseCase confirmarPagamentoUseCase;
    private final ObjectMapper objectMapper;

    @SqsListener("${app.fila-pedidos}")
    public void receiveMessage(String message) {
        log.info("Mensagem recebida da fila SQS: {}", message);
        try {
            Pagamento pagamentoConfirmado = objectMapper.readValue(message, Pagamento.class);

            if (pagamentoConfirmado != null && pagamentoConfirmado.getIdExterno() != null) {
                confirmarPagamentoUseCase.execute(pagamentoConfirmado.getIdExterno());
                log.info("Pagamento para o id externo {} confirmado com sucesso.", pagamentoConfirmado.getIdExterno());
            } else {
                log.warn("Mensagem da fila SQS com formato inválido.");
            }

        } catch (Exception e) {
            log.error("Erro ao processar mensagem da fila SQS.", e);
            // "dead-letter queue" (DLQ) para não perder mensagens com erro.
        }
    }
}