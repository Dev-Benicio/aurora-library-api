package com.auroralibrary.library.controller;

import com.auroralibrary.library.dto.modelResponse.EntityModelSingle;
import com.auroralibrary.library.service.ClientService;
import com.auroralibrary.library.dto.request.ClientCreateRequest;
import com.auroralibrary.library.dto.request.ClientUpdateRequest;
import com.auroralibrary.library.dto.response.ClientResponse;
import com.auroralibrary.library.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.auroralibrary.library.dto.modelResponse.EntityModel;

/* É uma classe Controller,
que define como o processamento deve ser realizado (CRUD) */
@Tag(name = "Clients", description = "Gerenciamento de clientes")
@SecurityRequirement(name = "bearer-key")
@Slf4j
@RestController
@RequestMapping("/clients")
public class ClientController {

    private static final Logger log = LoggerFactory.getLogger(ClientController.class);
    @Autowired
    private ClientService clientService;

    @Operation(summary = "Criar novo Cliente", description = "Cria um novo Cliente no sistema")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos ou CPF inválido", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<EntityModelSingle<ClientResponse>> create(@RequestBody @Valid ClientCreateRequest cliente) {
            ClientResponse createdCliente = clientService.create(cliente);
            log.info("[ClienteController] Cliente criado com sucesso | Method: POST | Status: {}",
                            HttpStatus.CREATED.value());

            EntityModelSingle<ClientResponse> response = new EntityModelSingle<>(createdCliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /* Busca todos os Clientes */
    @Operation(summary = "Listar todos os Clientes", description = "Retorna lista de todos os Clientes cadastrados")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ClientResponse.class)))),
                    @ApiResponse(responseCode = "404", description = "Nenhum Cliente encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<EntityModel<ClientResponse>> getClients(@PageableDefault(size = 20, sort = "name") Pageable pageable) {
            Page<ClientResponse> clients = clientService.findAll(pageable);

            log.info("[ClienteController] Clientes listados com sucesso | Method: GET | Status: {}",
                    HttpStatus.OK.value());
            EntityModel<ClientResponse> response = new EntityModel<>(clients);
        return ResponseEntity.ok(response);
    }

    /* Busca um Cliente pelo ID */
    @Operation(summary = "Buscar Cliente por ID", description = "Retorna um Cliente específico")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponse.class))),
                    @ApiResponse(responseCode = "400", description = "ID inválido", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModelSingle<ClientResponse>> getClient(@PathVariable("id") Long id) {
            ClientResponse client = clientService.findById(id);

            log.info("[ClientController] Cliente encontrado com sucesso - ID: {} | Method: GET | Status: {}",
                    client.id(), HttpStatus.OK.value());
            EntityModelSingle<ClientResponse> response = new EntityModelSingle<>(client);
            return ResponseEntity.ok(response);
    }


    @Operation(summary = "Listar todos os Clientes atraves do(s) filtro(s) solicitado(s)", description = "Retorna lista de todos os clientes que foi solicitado atraves do(s) filtro(s)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ClientResponse.class)))),
            @ApiResponse(responseCode = "404", description = "Nenhum Cliente encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/filters")
    public ResponseEntity<EntityModel<ClientResponse>> getClientsByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String cpf,
            @PageableDefault(size = 20, sort = "name") Pageable pageable
    ){

        Page<ClientResponse> clients = clientService.findByFilters(name, cpf, pageable);
        log.info("Clientes Encontrados atraves do(s) filtro(s)");

        EntityModel<ClientResponse> response = new EntityModel<>(clients);
        return ResponseEntity.ok(response);
    }

    /* Atualiza um Cliente */
    @Operation(summary = "Atualizar Cliente", description = "Atualiza dados de um Cliente existente")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos ou CPF inválido", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModelSingle<ClientResponse>> updateClient(@RequestBody @Valid ClientUpdateRequest cliente, @PathVariable("id") Long id) {
            ClientResponse updatedCliente = clientService.update(cliente, id);
            log.info("[ClientController] Cliente atualizado com sucesso - ID: {} | Method: PUT | Status: {}", id,
                    HttpStatus.OK.value());

            EntityModelSingle<ClientResponse> response = new EntityModelSingle<>(updatedCliente);
            return ResponseEntity.ok(response);
    }

    /* Exclui um cliente */
    @Operation(summary = "Excluir cliente", description = "Remove permanentemente um Cliente")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "204", description = "Cliente excluído com sucesso", content = @Content),
                    @ApiResponse(responseCode = "400", description = "ID inválido", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable("id") Long id) {
            clientService.delete(id);
            log.info("[ClienteController] Cliente excluído com sucesso - ID: {} | Status: {}",
                    id, HttpStatus.NO_CONTENT.value());
            return ResponseEntity.noContent().build();
    }
}
