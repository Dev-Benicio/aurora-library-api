package com.auroralibrary.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.auroralibrary.library.dto.request.AuthenticateRequest;
import com.auroralibrary.library.dto.response.AuthenticateResponse;
import com.auroralibrary.library.exception.ErrorResponse;
import com.auroralibrary.library.model.Administrator;
import com.auroralibrary.library.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Authentication", description = "Autenticação")
@Slf4j
@RestController
@RequestMapping("/login")
public class AuthenticationController {

  @Autowired
  private AuthenticationManager manager;

  @Autowired
  private TokenService tokenService;

  @Operation(summary = "Autentição do sistema", description = "Faz a autenticação do administrador")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Login autorizado com sucesso",
          content = @Content(schema = @Schema(implementation = AuthenticateResponse.class))),
      @ApiResponse(responseCode = "401", description = "Login ou senha invalidos",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "403", description = "Acesso não autorizado", 
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor", 
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PostMapping
  public ResponseEntity<AuthenticateResponse> login(@RequestBody @Valid AuthenticateRequest dados) {
    var autenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.password());
    var authenticate = manager.authenticate(autenticationToken);
    var response = new AuthenticateResponse(tokenService.generateToken(
          (Administrator) authenticate.getPrincipal()));

    log.info("Autenticação do administrador com sucesso");
    return ResponseEntity.ok(response);
  }
}
