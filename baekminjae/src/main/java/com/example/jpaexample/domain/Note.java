package com.example.jpaexample.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class Note {
    private final UUID id;

    private UUID trackId;
    private String text;
    private Double startTime;
    private Double endTime;

    public void update(UUID trackId, String text, Double startTime, Double endTime) {
        if (trackId != null) {
            this.trackId = trackId;
        }
        if (text != null) {
            this.text = text;
        }
        if (startTime != null) {
            this.startTime = startTime;
        }
        if (endTime != null) {
            this.endTime = endTime;
        }
    }
}
