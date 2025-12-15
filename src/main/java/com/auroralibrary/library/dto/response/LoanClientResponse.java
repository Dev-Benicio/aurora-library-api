package com.auroralibrary.library.dto.response;

public record LoanClientResponse(
    Long id,
    String name,
    String cpf
) {
}