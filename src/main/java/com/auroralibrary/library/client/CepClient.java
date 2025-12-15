package com.auroralibrary.library.client;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;
import com.auroralibrary.library.dto.response.CepResponse;
import com.auroralibrary.library.exception.ValidationException;

@Slf4j
@Service
public class CepClient {
  @Autowired
  private RestTemplate restTemplate;

  @Async
  @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 500))
  public CompletableFuture<CepResponse> validateAddress(String cep) {

    return CompletableFuture.supplyAsync(() -> {
        final String cepFormatado = cep.replaceAll("[^0-9]", "");

        final String URL_API = "https://brasilapi.com.br/api/cep/v1/" + cepFormatado;
        ResponseEntity<CepResponse> response = restTemplate.getForEntity(URL_API, CepResponse.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            log.error("Erro ao validar endereço: {}", response.getStatusCode());
            throw new ValidationException("Endereço inválido");
        }

        return response.getBody();
    }).orTimeout(10, TimeUnit.SECONDS);

  }
}
