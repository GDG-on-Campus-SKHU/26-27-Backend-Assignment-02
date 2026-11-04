package com.example.jpaexample.dto;

import com.example.jpaexample.domain.Genre;

public record BookSaveRequestDto(
        Long authorId,
        String title,
        Genre genre
) {
}
