package org.fiap.fastfoodpedidos.infrastructure.adapter.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fiap.fastfoodpedidos.application.port.driver.CancelarPagamentoUseCase;
import org.fiap.fastfoodpedidos.application.port.driver.ConfirmarPagamentoUseCase;
import org.fiap.fastfoodpedidos.domain.enumeration.PagamentoStatus;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.model.Message;

@Slf4j
@Component
@RequiredArgsConstructor
public class PagamentoSqsListener {

    private final ConfirmarPagamentoUseCase confirmarPagamentoUseCase;
    private final CancelarPagamentoUseCase cancelarPagamentoUseCase;
    private final ObjectMapper objectMapper;

    @SqsListener("${app.fila-pedidos}")
    public void receiveMessage(Message message) {

        String messageBody = message.body();
        log.info("Mensagem recebida da fila de resultado de pagamentos. Body: {}", messageBody);

        try {
            ResultadoPagamentoDTO resultado = objectMapper.readValue(messageBody, ResultadoPagamentoDTO.class);

            if (resultado == null || resultado.status() == null) {
                log.warn("Mensagem da fila SQS com formato inválido ou sem status. Body: {}", messageBody);
                return;
            }

            if (PagamentoStatus.REALIZADO.equals(resultado.status())) {
                log.info("Processando confirmação de pagamento para o pedido {}", resultado.pedidoId());
                confirmarPagamentoUseCase.execute(resultado.pedidoId());
                log.info("Pagamento confirmado e pedido atualizado com sucesso.");

            } else if (PagamentoStatus.CANCELADO.equals(resultado.status())) {
                log.info("Processando cancelamento de pagamento para o pedido {}", resultado.pedidoId());
                cancelarPagamentoUseCase.execute(resultado.pedidoId());
                log.info("Pagamento cancelado e pedido atualizado com sucesso.");

            } else {
                log.warn("Status de pagamento '{}' não suportado para processamento. Ignorando mensagem.", resultado.status());
            }

        } catch (Exception e) {
            log.error("Erro ao processar mensagem da fila SQS. Body: " + messageBody, e);
            throw new RuntimeException(e);
        }
    }
}