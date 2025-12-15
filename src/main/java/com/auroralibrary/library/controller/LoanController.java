package com.auroralibrary.library.controller;

import com.auroralibrary.library.dto.modelResponse.EntityModel;
import com.auroralibrary.library.dto.modelResponse.EntityModelSingle;
import com.auroralibrary.library.validation.StringFormatter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.auroralibrary.library.dto.request.LoanCreateRequest;
import com.auroralibrary.library.dto.request.LoanUpdateRequest;
import com.auroralibrary.library.dto.response.LoanResponse;
import com.auroralibrary.library.exception.ErrorResponse;
import com.auroralibrary.library.service.LoanService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;

/* É uma classe Controller,
que define como o processamento deve ser realizado (CRUD) */
@Tag(name = "Loans", description = "Gerenciamento de emprestimos")
@SecurityRequirement(name = "bearer-key")
@Slf4j
@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Operation(summary = "Criar novo empréstimo", description = "Registra um novo empréstimo no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Empréstimo criado com sucesso", 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoanResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", 
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "422", description = "Limite de empréstimos excedido",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<EntityModelSingle<LoanResponse>> create(@RequestBody @Valid LoanCreateRequest loan) {
        LoanResponse createdLoan = loanService.create(loan);
        log.info("[LoanController] Emprestimo criado com sucesso | Method: POST | Status: {}",
                HttpStatus.CREATED.value());

        EntityModelSingle<LoanResponse> response = new EntityModelSingle<>(createdLoan);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /* Métudo para buscar todos os emprestimos */
    @Operation(summary = "Listar todos os empréstimos", description = "Retorna todos os empréstimos cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = LoanResponse.class)))),
        @ApiResponse(responseCode = "404", description = "Nenhum empréstimo encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<EntityModel<LoanResponse>> getLoans(@PageableDefault(size = 20, sort =
            "loanDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<LoanResponse> loans = loanService.findAll(pageable);
        log.info("Emprestimos encontrados");

        EntityModel<LoanResponse> response = new EntityModel<>(loans);
        return ResponseEntity.ok(response);
    }

    /* Método para buscar um emprestimo pelo id */
    @Operation(summary = "Buscar empréstimo por ID", description = "Retorna um empréstimo específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empréstimo encontrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoanResponse.class))),
            @ApiResponse(responseCode = "400", description = "ID inválido", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModelSingle<LoanResponse>> getLoan(@PathVariable("id") Long id) {
            LoanResponse loan = loanService.findById(id);
            log.info("Emprestimo encontrado");

            EntityModelSingle<LoanResponse> response = new EntityModelSingle<>(loan);
            return ResponseEntity.ok(response);
    }


    @Operation(summary = "Lista todos os empréstimos atraves do(s) filtro(s) solicitado(s)", description = "Retorna todos os empréstimos que foram solicitados atraves do(s) filtro(s)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = LoanResponse.class)))),
            @ApiResponse(responseCode = "404", description = "Nenhum empréstimo encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/filters")
    public ResponseEntity<EntityModel<LoanResponse>> getLoansFilter(
            @PageableDefault(size = 20, sort = "loanDate", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) Boolean isCompleted,
            @RequestParam(required = false) LocalDate loanDate,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String publisher
    ) {

        Page<LoanResponse> loans = loanService.findByFilters(isCompleted, loanDate, name, cpf, title, publisher, pageable);
        log.info("Emprestimos encontrados atraves do(s) filtro(s)");

        EntityModel<LoanResponse> response = new EntityModel<>(loans);
        return ResponseEntity.ok(response);
    }

    /* Método para atualizar um emprestimo */
    @Operation(summary = "Atualizar empréstimo", description = "Atualiza um empréstimo existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empréstimo atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoanResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "Limite de empréstimos excedido", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModelSingle<LoanResponse>> updateLoan(@RequestBody @Valid LoanUpdateRequest loan, @PathVariable("id") Long id) {
        LoanResponse updateloan = loanService.update(loan, id);
        log.info("Emprestimo atualizado");

        EntityModelSingle<LoanResponse> response = new EntityModelSingle<>(updateloan);
        return ResponseEntity.ok(response);
    }

    /* Método para excluir um emprestimo */
    @Operation(summary = "Excluir empréstimo", description = "Remove permanentemente um empréstimo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Empréstimo excluído com sucesso", content = @Content),
            @ApiResponse(responseCode = "400", description = "ID inválido", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable("id") Long id) {
        loanService.delete(id);
        log.info("Emprestimo excluído");
        return ResponseEntity.noContent().build();
    }
}
