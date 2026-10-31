package com.gdg.gdg_be_assignment02.dto;

import com.gdg.gdg_be_assignment02.domain.Movie;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MovieInfoResponseDto(
        Long movieId,
        String title,
        String director,
        LocalDate releaseDate,
        Long id, // userId
        String name // userName
) {
    public static MovieInfoResponseDto from(Movie movie) {
        return MovieInfoResponseDto.builder()
                .movieId(movie.getMovieId())
                .title(movie.getTitle())
                .director(movie.getDirector())
                .releaseDate(movie.getReleaseDate())
                .id(movie.getUser().getId())
                .name(movie.getUser().getName())
                .build();
    }
}