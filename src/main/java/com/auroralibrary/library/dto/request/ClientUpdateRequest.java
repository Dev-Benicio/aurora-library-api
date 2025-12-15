package com.auroralibrary.library.dto.request;

import java.util.Optional;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record ClientUpdateRequest(
    @Size(max = 100)
    String name,

    @Size(min = 10, max = 16)
    String phone,

    @Email
    @Size(max = 100)
    String email,

    Optional<AddressUpdateRequest> address
) {
}
