package com.gdg.jpaexample.dto;

import java.time.LocalDate;

public record TrackSaveRequestDto (
        Long artistId,
        String title,
        String album,
        LocalDate releaseDate
){
}
