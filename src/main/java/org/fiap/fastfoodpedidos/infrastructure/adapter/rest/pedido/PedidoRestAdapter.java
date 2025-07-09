package org.fiap.fastfoodpedidos.infrastructure.adapter.rest.pedido;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fiap.fastfoodpedidos.application.port.driver.*;
import org.fiap.fastfoodpedidos.domain.model.Pedido;
import org.fiap.fastfoodpedidos.infrastructure.adapter.HeaderUtil;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.ClienteEntity;
import org.fiap.fastfoodpedidos.infrastructure.adapter.rest.pedido.dto.*;
import org.fiap.fastfoodpedidos.infrastructure.adapter.rest.pedido.mapper.PedidoRestMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
public class PedidoRestAdapter {


    private final CriarPedidoUseCase criarPedidoUseCase;
    private final BuscarPedidoUseCase buscarPedidoUseCase;
    private final BuscarPedidosUseCase buscarPedidosUseCase;
    private final BuscarPedidosEmAndamentoUseCase buscarPedidosEmAndamentoUseCase;
    private final AtualizarStatusPedidoUseCase atualizarStatusPedidoUseCase;
    private final PedidoRestMapper pedidoRestMapper;

    @Value("${app.name}")
    private String applicationName;
    public static final String ENTITY_NAME = ClienteEntity.class.getName();

    @GetMapping(value = "/{id}")
    @Operation(summary = "Busca um pedido pelo id")
    public ResponseEntity<DetalhePedidoResponse> getPedido(@PathVariable @Valid final Integer id) {
        Pedido pedido = this.buscarPedidoUseCase.execute(id);
        DetalhePedidoResponse response = this.pedidoRestMapper.toDetalhePedidoResponse(pedido);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Lista os pedidos")
    public ResponseEntity<List<DetalhePedidoResponse>> getPedidos() {
        List<Pedido> pedidos = this.buscarPedidosUseCase.execute();
        List<DetalhePedidoResponse> response = pedidos.stream().map(this.pedidoRestMapper::toDetalhePedidoResponse).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/em-andamento")
    @Operation(summary = "Lista os pedidos em andamento")
    public ResponseEntity<List<DetalhePedidoEmPreparacaoResponse>> getPedidosEmPreparacao() {
        List<Pedido> pedidos = this.buscarPedidosEmAndamentoUseCase.execute();
        List<DetalhePedidoEmPreparacaoResponse> response = pedidos.stream().map(this.pedidoRestMapper::toDetalhePedidoEmPreparacaoResponse).toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Cria um pedido")
    public ResponseEntity<RegistrarPedidoResponse> createPedido(@RequestBody @Valid final RegistrarPedidoRequest registrarPedidoRequest) throws URISyntaxException {
        log.debug("REST request to save Pedido : {}", registrarPedidoRequest);
        Pedido pedido = this.pedidoRestMapper.toPedido(registrarPedidoRequest);
        pedido = this.criarPedidoUseCase.execute(pedido);

        RegistrarPedidoResponse response = this.pedidoRestMapper.toRegistrarPedidoResponse(pedido);

        return ResponseEntity
                .created(new URI("/api/v1/pedidos" + response.id()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, response.id().toString()))
                .body(response);
    }

    @PostMapping(value = "/{id}/atualizar-status")
    @Operation(summary = "Atualiza o status de um pedido")
    public ResponseEntity<RegistrarPedidoResponse> atualizarStatusPedido(@PathVariable @Valid final Integer id, @RequestBody @Valid final AtualizarStatusRequest atualizarStatusRequest) throws URISyntaxException {
        log.debug("REST request to atualizar-status Pedido : {}", atualizarStatusRequest);
        Pedido pedido = this.atualizarStatusPedidoUseCase.execute(id, atualizarStatusRequest.status());
        RegistrarPedidoResponse response = this.pedidoRestMapper.toRegistrarPedidoResponse(pedido);
        return ResponseEntity
                .created(new URI("/api/v1/pedidos" + response.id()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, response.id().toString()))
                .body(response);
    }


}
