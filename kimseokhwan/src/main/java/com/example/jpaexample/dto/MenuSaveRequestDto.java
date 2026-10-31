package com.example.jpaexample.dto;

public record MenuSaveRequestDto(
        Long categoryId,
        String name,
        int price
) {
}
