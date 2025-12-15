package com.auroralibrary.library.dto.request;

import java.time.Year;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

public record BookUpdateRequest(
    @Size(max = 80)
    String title,

    @Size(max = 100)
    String category,

    @Size(max = 100)
    String author,

    @Size(max = 100)
    String publisher,

    @JsonFormat(pattern = "yyyy", shape = JsonFormat.Shape.STRING)
    @Schema(example = "2024")
    Year year,
    
    Long quantityBooks,
    String bookCover
) {
}
