package com.auroralibrary.library.dto.request;

import java.time.LocalDate;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record LoanCreateRequest(
    @NotNull
    Long idClient,

    @NotNull 
    Long idBook,

    @NotNull
    @PastOrPresent
    LocalDate loanDate,

    @NotNull
    @FutureOrPresent
    LocalDate limitDate
) {
}
