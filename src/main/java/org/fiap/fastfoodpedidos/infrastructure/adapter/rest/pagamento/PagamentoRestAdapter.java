package org.fiap.fastfoodpedidos.infrastructure.adapter.rest.pagamento;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fiap.fastfoodpedidos.application.port.driver.IniciarPagamentoUseCase;
import org.fiap.fastfoodpedidos.infrastructure.adapter.rest.pagamento.dto.IniciarPagamentoRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pagamentos")
@RequiredArgsConstructor
public class PagamentoRestAdapter {

    private final IniciarPagamentoUseCase iniciarPagamentoUseCase;

    @PostMapping("/iniciar")
    @Operation(summary = "Inicia o pagamento de um pedido e retorna o QR Code")
    public ResponseEntity<Resource> iniciarPagamento(@RequestBody @Valid IniciarPagamentoRequest request) {

        Resource qrCodeResource = iniciarPagamentoUseCase.execute(request.getPedidoId());

        if (qrCodeResource == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"qrcode.png\"")
                .body(qrCodeResource);
    }
}