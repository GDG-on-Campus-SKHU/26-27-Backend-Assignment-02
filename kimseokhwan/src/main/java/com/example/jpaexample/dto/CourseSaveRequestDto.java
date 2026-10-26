package com.example.jpaexample.dto;

public record CourseSaveRequestDto(
        String courseName,
        String professor
) {
}