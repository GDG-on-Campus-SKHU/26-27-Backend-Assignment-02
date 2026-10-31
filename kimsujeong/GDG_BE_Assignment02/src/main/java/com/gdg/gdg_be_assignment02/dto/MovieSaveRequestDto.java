package com.gdg.gdg_be_assignment02.dto;

import java.time.LocalDate;

public record MovieSaveRequestDto(
    String title,
    String director,
    LocalDate releaseDate
) {
}
