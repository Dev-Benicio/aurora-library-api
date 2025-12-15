package com.auroralibrary.library.dto.request;

import jakarta.validation.constraints.Size;

public record AddressUpdateRequest(
    Integer number,

    @Size(min = 8, max = 9)
    String cep
) {
}
