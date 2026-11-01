package com.example.jpaexample.dto.track;

public record TrackUpdateRequestDto(
        String name,
        Double gainDb,
        Boolean muted
) {
}
