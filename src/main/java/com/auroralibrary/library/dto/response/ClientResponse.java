package com.auroralibrary.library.dto.response;

public record ClientResponse(
    Long id,
    String name,
    String phone,
    String email,
    String cpf,
    AddressResponse address
) {
}
