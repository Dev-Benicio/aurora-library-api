package com.auroralibrary.library.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LoanResponse(
    Long id_loan,
    LocalDate loanDate,
    LocalDate limitDate,
    LocalDate deliveryDate,
    BigDecimal penalty,
    boolean is_completed,
    boolean fine_status,
    LoanClientResponse client,
    LoanBookResponse book) {
}
