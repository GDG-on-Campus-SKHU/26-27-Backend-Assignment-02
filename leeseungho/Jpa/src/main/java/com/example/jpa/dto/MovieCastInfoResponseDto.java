package com.example.jpa.dto;

import com.example.jpa.domain.MovieCast;
import lombok.Builder;

@Builder
public record MovieCastInfoResponseDto(
        Long id,
        String name,
        String role,
        Integer age,
        Long movieId,
        String title
) {

    public static MovieCastInfoResponseDto from(MovieCast movieCast) {
        return MovieCastInfoResponseDto.builder()
                .id(movieCast.getId())
                .name(movieCast.getName())
                .role(movieCast.getRole())
                .age(movieCast.getAge())
                .movieId(movieCast.getMovie().getId())
                .title(movieCast.getMovie().getTitle())
                .build();
    }
}
