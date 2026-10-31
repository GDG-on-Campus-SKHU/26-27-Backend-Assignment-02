package com.gdg.jpaexample.dto;

public record   ArtistSaveRequestDto (
    String stageName,
    String nationality,
    Integer debutYear
){
}
