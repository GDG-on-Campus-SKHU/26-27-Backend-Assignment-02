package com.gdg.gdg_be_assignment02.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record MovieSaveRequestDto(
    @NotBlank(message = "제목은 필수입니다.")
    String title,

    String director,

    @PastOrPresent(message = "개봉일은 현재 또는 과거 날짜여야 합니다.")
    LocalDate releaseDate
) {
}
