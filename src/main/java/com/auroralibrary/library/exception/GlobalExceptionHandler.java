package com.auroralibrary.library.exception;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(ValidationException e) {
    log.error("Erro de validação: {}", e.getMessage());
    return new ResponseEntity<>(
            new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Erro de validação", "validation", e.getMessage()),
            HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
    log.error("Recurso não encontrado: {}", e.getMessage());
    return new ResponseEntity<>(
            new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Recurso não encontrado", "resource", e.getMessage()),
            HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(DatabaseException.class)
  public ResponseEntity<ErrorResponse> handleDatabaseException(DatabaseException e) {
    log.error("Erro de banco de dados: {}", e.getMessage());
    return new ResponseEntity<>(
            new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro interno", "database", "Não foi possível processar sua solicitação"),
            HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(InvalidParameterException.class)
  public ResponseEntity<ErrorResponse> handleInvalidParameterException(InvalidParameterException e) {
    return new ResponseEntity<>(
            new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Parâmetro inválido", "parameter", e.getMessage()),
            HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
    log.error("Erro no tipo do parâmetro: {}", e.getMessage());
    return new ResponseEntity<>(
            new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Parâmetro inválido", "id", "O ID deve ser um número válido"),
            HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(LoanLimitExceededException.class)
  public ResponseEntity<ErrorResponse> handleLoanLimitExceededException(LoanLimitExceededException e) {
    log.error("Limite de empréstimos excedido: {}", e.getMessage());
    return new ResponseEntity<>(
            new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Limite excedido", "loan", e.getMessage()),
            HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(ValidationCepException.class)
  public ResponseEntity<ErrorResponse> handleValidationCepException(ValidationCepException e) {
    log.error("Erro na validação do CEP: {}", e.getMessage());
    return new ResponseEntity<>(
            new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "CEP inválido", "cep", e.getMessage()),
            HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({
          UsernameNotFoundException.class,
          BadCredentialsException.class,
          InternalAuthenticationServiceException.class
  })
  public ResponseEntity<ErrorResponse> handleAuthenticationException(Exception e) {
    log.error("Erro de autenticação: {}", e.getMessage());
    return new ResponseEntity<>(
            new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Falha na autenticação", "auth", "Cliente inexistente ou senha inválida"),
            HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
    List<ErrorResponse.Issue> issues = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> new ErrorResponse.Issue(error.getField(), error.getDefaultMessage()))
            .collect(Collectors.toList());

    ErrorResponse response = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Erro de validação",
            "validation",
            "Campos inválidos"
    );
    response.getError().setIssues(issues);

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<Void> handleNoResourceFoundException(NoResourceFoundException e) {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<ErrorResponse> handleConflict(ConflictException e) {
    return new ResponseEntity<>(
            new ErrorResponse(HttpStatus.CONFLICT.value(), "Dados inválidos", "conflict", e.getMessage()),
            HttpStatus.CONFLICT);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
    log.error("Erro não tratado: {} - Tipo: {}", e.getMessage(), e.getClass().getName());
    return new ResponseEntity<>(
            new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro interno", "system", e.getMessage()),
            HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
