package com.auroralibrary.library.controller;

import com.auroralibrary.library.dto.modelResponse.EntityModel;
import com.auroralibrary.library.dto.modelResponse.EntityModelSingle;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import com.auroralibrary.library.dto.request.BookCreateRequest;
import com.auroralibrary.library.dto.request.BookUpdateRequest;
import com.auroralibrary.library.dto.response.BookResponse;
import com.auroralibrary.library.exception.ErrorResponse;
import com.auroralibrary.library.service.BookService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Books", description = "Gerenciamento de livros")
@SecurityRequirement(name = "bearer-key")
@Slf4j
@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Operation(summary = "Criar novo livro", description = "Cria um novo livro no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Livro criado com sucesso", 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", 
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", 
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<EntityModelSingle<BookResponse>> create(
            @RequestPart("book") @Valid BookCreateRequest book,
            @RequestPart("image") MultipartFile image
    ) {
        BookResponse createdBook = bookService.create(book, image);
        log.info("[BookController] Livro criado com sucesso | Method: POST | Status: {}",
                HttpStatus.CREATED.value());

        EntityModelSingle<BookResponse> response = new EntityModelSingle<>(createdBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /* Método para buscar todos os livros */
    @Operation(summary = "Listar todos os livros", description = "Retorna lista completa dos livros cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BookResponse.class)))),
        @ApiResponse(responseCode = "404", description = "Nenhum livro encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<EntityModel<BookResponse>> findAll(@ParameterObject @PageableDefault(size = 20, sort = "title") Pageable pageable) {
        Page<BookResponse> books = bookService.findAll(pageable);
        log.info("Books listados com sucesso");

        EntityModel<BookResponse> response = new EntityModel<>(books);
        return ResponseEntity.ok(response);
    }

    /* Método para buscar um livro pelo id */
    @Operation(summary = "Buscar livro por ID", description = "Retorna um livro específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro encontrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponse.class))),
            @ApiResponse(responseCode = "400", description = "ID inválido", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModelSingle<BookResponse>> findById(@PathVariable("id") Long id) {
            BookResponse book = bookService.findById(id);
            log.info("[BookController] Livro encontrado com sucesso - ID: {} | Method: GET | Status: {}",
                    book.id(), HttpStatus.OK.value());

            EntityModelSingle<BookResponse> response = new EntityModelSingle<>(book);
            return ResponseEntity.ok(response); // HTTP 200
    }

    @Operation(summary = "Lista os livros atraves do(s) filtro(s) solicitado(s)", description = "Retorna lista de livros da categoria ou titulo escolhido(a)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BookResponse.class)))),
            @ApiResponse(responseCode = "404", description = "Nenhum livro encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/filters")
    public ResponseEntity<EntityModel<BookResponse>> findFilters(
            @ParameterObject
            @PageableDefault(size = 20, sort = "title") Pageable pageable,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String category
    ) {
        Page<BookResponse> books = bookService.findByFilters(title, category, pageable);
        log.info("Books listados com sucesso atraves do(s) filtro(s)");

        EntityModel<BookResponse> response = new EntityModel<>(books);
        return ResponseEntity.ok(response);
    }

    /* Método para atualizar um livro */
    @Operation(summary = "Atualizar livro", description = "Atualiza dados de um livro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModelSingle<BookResponse>> updateBook(@RequestBody @Valid BookUpdateRequest book, @PathVariable("id") Long id) {
        BookResponse upbook =  bookService.update(book, id);
        log.info("[BookController] Livro atualizado com sucesso - ID: {} | Method: PUT | Status: {}",
                id, HttpStatus.OK.value());

        EntityModelSingle<BookResponse> response = new EntityModelSingle<>(upbook);
        return ResponseEntity.ok(response);
    }

    /* Método para excluir um livro */
    @Operation(summary = "Excluir livro", description = "Remove permanentemente um livro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Livro excluído com sucesso", content = @Content),
            @ApiResponse(responseCode = "400", description = "ID inválido", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        log.info("[BookController] Livro excluído com sucesso - ID: {} | Method: DELETE | Status: {}",
                id, HttpStatus.NO_CONTENT.value());
        return ResponseEntity.noContent().build(); // HTTP 204
    }
}