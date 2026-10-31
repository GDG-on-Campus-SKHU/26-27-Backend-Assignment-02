package com.gdg.jpaexample.dto;

import com.gdg.jpaexample.domain.Artist;
import lombok.Builder;

@Builder
public record ArtistInfoResponseDto(
    Long id,
    String stageName,
    String nationality,
    Integer debutYear
) {
    public static ArtistInfoResponseDto from(Artist artist) {
        return ArtistInfoResponseDto.builder()
                .id(artist.getId())
                .stageName(artist.getStageName())
                .nationality(artist.getNationality())
                .debutYear(artist.getDebutYear())
                .build();
    }
}
