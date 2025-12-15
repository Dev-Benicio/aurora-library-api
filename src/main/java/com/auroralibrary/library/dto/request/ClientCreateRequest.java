package com.auroralibrary.library.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ClientCreateRequest(
    @NotBlank(message = "Nome invalido")
    @Size(max = 100)
    String name,

    @NotBlank(message = "Telefone invalido")
    @Size(min = 11, max = 16)
    String phone,

    @NotBlank(message = "Email invalido")
    @Size(max = 100)
    @Email(message = "Email invalido")
    String email,

    @NotBlank(message = "CPF invalido")
    @Size(min = 11, max = 14, message = "CPF invalido")
    String cpf,

    @NotNull (message = "Endereço invalido")
    @Valid
    AddressCreateRequest address
) {
}
