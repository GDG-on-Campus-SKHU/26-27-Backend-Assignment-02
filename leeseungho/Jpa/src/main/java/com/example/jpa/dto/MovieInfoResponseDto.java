package com.example.jpa.dto;

import com.example.jpa.domain.Movie;
import lombok.Builder;

@Builder
public record MovieInfoResponseDto(
        Long id,
        String title,
        String editor
) {

    public static MovieInfoResponseDto from(Movie movie) {
        return MovieInfoResponseDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .editor(movie.getEditor())
                .build();
    }
}
