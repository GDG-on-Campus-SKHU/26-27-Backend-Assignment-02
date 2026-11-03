package com.gdg.jpa.dto;

public record BookUpdateRequestDto(
        String title,
        Integer price
) {
}
