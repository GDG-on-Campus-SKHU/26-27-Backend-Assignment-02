package com.gdg.gdg_be_assignment02.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record MovieSaveRequestDto(
    @NotBlank(message = "제목은 필수입니다.")
    String title,

    String director,
    LocalDate releaseDate
) {
}
