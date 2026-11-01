package com.example.jpaexample.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class AudioClip {
    private final UUID id;

    private UUID trackId;
    private String audioPath;
    private Double position;
    private Double sourceStart;
    private Double sourceEnd;

    public void update(UUID trackId, String audioPath, Double position, Double sourceStart, Double sourceEnd) {
        if (trackId != null) {
            this.trackId = trackId;
        }
        if (audioPath != null) {
            this.audioPath = audioPath;
        }
        if (position != null) {
            this.position = position;
        }
        if (sourceStart != null) {
            this.sourceStart = sourceStart;
        }
        if (sourceEnd != null) {
            this.sourceEnd = sourceEnd;
        }
    }
}
