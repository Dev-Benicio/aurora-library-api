package com.auroralibrary.library.dto.request;

import java.time.LocalDate;
import jakarta.validation.constraints.PastOrPresent;

public record LoanUpdateRequest(
    @PastOrPresent
    LocalDate deliveryDate,
    Boolean isCompleted,
    Boolean fineStatus
) {
}
