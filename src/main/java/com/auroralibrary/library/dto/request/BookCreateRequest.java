package com.auroralibrary.library.dto.request;

import java.time.Year;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookCreateRequest(
    @NotBlank(message = "O campo título é obrigatório")
    @Size(max = 80)
    String title,

    @NotBlank
    @Size(max = 100)
    String category,

    @NotBlank
    @Size(max = 100)
    String author,

    @NotBlank
    @Size(max = 100)
    String publisher,

    @NotNull
    @JsonFormat(pattern = "yyyy", shape = JsonFormat.Shape.STRING)
    @Schema(example = "2024")
    Year year,

    @NotNull
    @Min(1)
    @Schema(description = "Quantidade de livro invalido", example = "1", minimum = "1")
    Long quantityBooks
) {
}
