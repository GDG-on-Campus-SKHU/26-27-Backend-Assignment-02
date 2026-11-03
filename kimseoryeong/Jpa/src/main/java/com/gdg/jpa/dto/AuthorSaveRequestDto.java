package com.gdg.jpa.dto;

public record AuthorSaveRequestDto(
        String authorName,
        String country
) {
}
