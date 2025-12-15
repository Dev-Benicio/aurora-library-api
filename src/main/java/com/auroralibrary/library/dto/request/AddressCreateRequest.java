package com.auroralibrary.library.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddressCreateRequest(
    @NotNull(message = "O campo numero é obrigatório")
    Integer number,

    @NotBlank(message = "O campo cep é obrigatório")
    @Size(min = 8, max = 9)
    String cep
){
}
