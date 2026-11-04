package com.example.jpa.dto;

public record MenuSaveRequestDto(
        Long categoryId,
        String name,
        Integer price
) {
}
