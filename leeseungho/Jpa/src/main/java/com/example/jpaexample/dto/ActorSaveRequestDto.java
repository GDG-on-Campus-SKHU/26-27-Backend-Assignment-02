package com.example.jpaexample.dto;

public record ActorSaveRequestDto(
        Long movieId,
        String name,
        String role,
        int age
) {
}
