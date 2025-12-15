package com.auroralibrary.library.controller;

import com.auroralibrary.library.dto.modelResponse.EntityModelSingle;
import com.auroralibrary.library.dto.response.AdministratorResponse;
import com.auroralibrary.library.exception.ErrorResponse;
import com.auroralibrary.library.service.AdministratorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Administrator", description = "Administrador")
@RestController
@RequestMapping("/administrator")
public class AdministratorController {
    @Autowired
    private AdministratorService administratorService;

    @Operation(summary = "Buscar Administrador por ID", description = "Retorna o Administrador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrador encontrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdministratorResponse.class))),
            @ApiResponse(responseCode = "400", description = "ID inválido", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Administrador não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModelSingle<AdministratorResponse>> findById(@PathVariable("id") Long id) {
        AdministratorResponse admin = administratorService.getAdministrator(id);
        log.info("[AdministratorController] Administrador encontrado com sucesso - ID: {} | Method: GET | Status: {}",
                admin.id(), HttpStatus.OK.value());

        EntityModelSingle<AdministratorResponse> response = new EntityModelSingle<>(admin);
        return ResponseEntity.ok(response);
    }
}
