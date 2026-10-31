package com.example.jpa.dto;

import com.example.jpa.domain.Actor;
import lombok.Builder;

@Builder
public record ActorInfoResponseDto(
        Long id,
        String name,
        String role,
        int age,
        Long movieId,
        String movieName
) {

    public static ActorInfoResponseDto from(Actor actor) {
        return ActorInfoResponseDto.builder()
                .id(actor.getId())
                .name(actor.getName())
                .role(actor.getRole())
                .age(actor.getAge())
                .movieId(actor.getMovie().getId())
                .movieName(actor.getMovie().getMovieName())
                .build();
    }
}
