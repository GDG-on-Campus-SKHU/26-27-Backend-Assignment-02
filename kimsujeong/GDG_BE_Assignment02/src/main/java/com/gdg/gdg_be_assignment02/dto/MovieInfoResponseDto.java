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
        Long userId,
        String userName
) {
    public static MovieInfoResponseDto from(Movie movie) {
        return MovieInfoResponseDto.builder()
                .movieId(movie.getMovieId())
                .title(movie.getTitle())
                .director(movie.getDirector())
                .releaseDate(movie.getReleaseDate())
                .userId(movie.getUser().getId())
                .userName(movie.getUser().getName())
                .build();
    }
}
