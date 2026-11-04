package com.example.dto;

public record MenuSaveRequestDto(
        Long categoryId,
        String name,
        Integer price
) {
}
