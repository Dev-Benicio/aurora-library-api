package com.auroralibrary.library.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Year;

public record BookResponse(
    Long id,
    String title,
    String category,
    String author,
    String publisher,

    @Schema(example = "2024")
    Year year,
    Long quantityBooks,
    String bookCover
) {
}
