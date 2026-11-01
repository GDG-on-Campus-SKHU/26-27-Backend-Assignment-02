package com.gdg.jpa.dto;

public record BookSaveRequestDto(
        Long authorId,
        String title,
        int price
) {
}
