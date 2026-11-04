package com.example.jpa.dto;

public record MovieCastSaveRequestDto(
        Long movieId,
        String name,
        String role,
        Integer age
) {
}
