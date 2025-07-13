package org.fiap.fastfoodpedidos.infrastructure.adapter.rest.cliente;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fiap.fastfoodpedidos.application.port.driver.CriarClienteUseCase;
import org.fiap.fastfoodpedidos.domain.model.Cliente;
import org.fiap.fastfoodpedidos.infrastructure.adapter.HeaderUtil;
import org.fiap.fastfoodpedidos.infrastructure.adapter.persistence.entity.ClienteEntity;
import org.fiap.fastfoodpedidos.infrastructure.adapter.rest.cliente.dto.RegistrarClienteRequest;
import org.fiap.fastfoodpedidos.infrastructure.adapter.rest.cliente.dto.RegistrarClienteResponse;
import org.fiap.fastfoodpedidos.infrastructure.adapter.rest.cliente.mapper.ClienteRestMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ClienteRestAdapter {


    private final CriarClienteUseCase criarClienteUseCase;
    private final ClienteRestMapper clienteRestMapper;

    @Value("${app.name}")
    private String applicationName;
    public static final String ENTITY_NAME = ClienteEntity.class.getName();


    @PostMapping(value = "/clientes")
    public ResponseEntity<RegistrarClienteResponse> createProduct(@RequestBody @Valid final RegistrarClienteRequest registrarClienteRequest) throws URISyntaxException {
        log.debug("REST request to save Cliente : {}", registrarClienteRequest);
        Cliente cliente = this.clienteRestMapper.toCliente(registrarClienteRequest);
        cliente = this.criarClienteUseCase.execute(cliente);

        RegistrarClienteResponse response = this.clienteRestMapper.toRegistrarClienteResponse(cliente);

        return ResponseEntity
                .created(new URI("/api/v1/clientes/" + response.id()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, response.id().toString()))
                .body(response);

    }

}
