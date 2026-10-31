package com.example.jpa.dto;

import com.example.jpa.domain.Movie;
import lombok.Builder;

@Builder
public record MovieInfoResponseDto(
        Long id,
        String movieName,
        String editor
) {

    public static MovieInfoResponseDto from(Movie movie) {
        return MovieInfoResponseDto.builder()
                .id(movie.getId())
                .movieName(movie.getMovieName())
                .editor(movie.getEditor())
                .build();
    }
}
