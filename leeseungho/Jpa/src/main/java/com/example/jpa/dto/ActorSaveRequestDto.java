package com.example.jpa.dto;

public record ActorSaveRequestDto(
        Long movieId,
        String name,
        String role,
        int age
) {
}
