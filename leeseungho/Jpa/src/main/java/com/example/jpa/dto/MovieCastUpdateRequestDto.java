package com.example.jpa.dto;

public record MovieCastUpdateRequestDto(
        String name,
        String role,
        Integer age,
        Long movieId
) {
}
