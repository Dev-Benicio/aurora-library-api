package com.auroralibrary.library.dto.response;

public record LoanBookResponse(
    Long id,
    String title,
    String bookCover
) {
}