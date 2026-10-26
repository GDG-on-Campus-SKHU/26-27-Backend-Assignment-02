package com.example.jpaexample.dto;

public record StudentSaveRequestDto(
        Long courseId,
        String name,
        String major
) {
}