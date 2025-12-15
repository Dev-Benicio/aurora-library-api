package com.auroralibrary.library.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthenticateRequest(
  @NotBlank(message = "UserName esta vazio ou nulo")
  @Size(min = 4, max = 20, message = "Tamanho do UserName inválido")
  @Email
  String login,

  @NotBlank(message = "Campo Senha está vazio ou nulo")
  @Size(min = 4, message = "Tamanho da Senha está inválido")
  String password
) {
}
