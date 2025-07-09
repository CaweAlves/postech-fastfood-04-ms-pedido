package org.fiap.fastfoodpedidos.domain.enumeration;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public enum PedidoStatus {
    FINALIZADO("Finalizado") {
        @Override
        public List<PedidoStatus> transicoesPossiveis() {
            return Collections.emptyList();
        }
    },
    CANCELADO("Cancelado") {
        @Override
        public List<PedidoStatus> transicoesPossiveis() {
            return Collections.emptyList();
        }
    },
    PRONTO("Pronto") {
        @Override
        public List<PedidoStatus> transicoesPossiveis() {
            return List.of(FINALIZADO, CANCELADO);
        }
    },
    EM_PREPARACAO("Em preparação") {
        @Override
        public List<PedidoStatus> transicoesPossiveis() {
            return List.of(PRONTO, CANCELADO);
        }
    },
    RECEBIDO("Recebido") {
        @Override
        public List<PedidoStatus> transicoesPossiveis() {
            return List.of(EM_PREPARACAO, CANCELADO);
        }
    },
    AGUARDANDO_PAGAMENTO("Aguardando Pagamento") {
        @Override
        public List<PedidoStatus> transicoesPossiveis() {
            return List.of(RECEBIDO, CANCELADO);
        }
    };

    private final String value;

    public abstract List<PedidoStatus> transicoesPossiveis();

    PedidoStatus(String value) {
        this.value = value;

    }

    public boolean podeTransicionarPara(PedidoStatus novoStatus) {
        return this.transicoesPossiveis().contains(novoStatus);
    }

}