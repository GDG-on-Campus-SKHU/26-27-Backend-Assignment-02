package com.example.jpaexample.dto.track;

import com.example.jpaexample.domain.Track;
import lombok.Builder;

@Builder
public record TrackResponseDto(
        Long id,
        String name,
        Double gainDb,
        Boolean muted,
        Long projectId
) {

    public static TrackResponseDto from(Track track) {
        return TrackResponseDto.builder()
                .id(track.getId())
                .name(track.getName())
                .gainDb(track.getGainDb())
                .muted(track.getMuted())
                .projectId(track.getProject().getId())
                .build();
    }
}
