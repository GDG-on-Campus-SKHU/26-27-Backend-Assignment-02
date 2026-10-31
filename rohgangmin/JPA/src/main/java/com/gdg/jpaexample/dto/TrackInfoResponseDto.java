package com.gdg.jpaexample.dto;

import com.gdg.jpaexample.domain.Track;
import lombok.Builder;
import java.time.LocalDate;

@Builder
public record TrackInfoResponseDto(
        Long id,
        String title,
        String album,
        LocalDate releaseDate,
        Long artistId,
        String artistName
) {
    public static TrackInfoResponseDto from(Track track) {
        return TrackInfoResponseDto.builder()
                .id(track.getId())
                .title(track.getTitle())
                .album(track.getAlbum())
                .releaseDate(track.getReleaseDate())
                .artistId(track.getArtist().getId())
                .artistName(track.getArtist().getStageName())
                .build();
    }
}
