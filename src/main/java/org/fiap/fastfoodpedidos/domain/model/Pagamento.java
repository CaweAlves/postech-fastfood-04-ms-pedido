package org.fiap.fastfoodpedidos.domain.model;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fiap.fastfoodpedidos.domain.enumeration.PagamentoStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {

    private Integer id;
    private String idExterno;
    @Valid
    private Pedido pedido;
    private PagamentoStatus status;
    private String qrCode;

}
