package com.gdg.jpa.dto;

public record ArtistSaveRequestDto(
        String stageName,
        String nationality,
        Integer debutYear
) {
}
