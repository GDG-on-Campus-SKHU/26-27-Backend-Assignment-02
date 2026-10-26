package com.example.jpaexample.dto;

import com.example.jpaexample.domain.Course;
import lombok.Builder;

@Builder
public record CourseInfoResponseDto(
        Long id,
        String courseName,
        String professor
) {
    public static CourseInfoResponseDto from(Course course) {
        return CourseInfoResponseDto.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .professor(course.getProfessor())
                .build();
    }
}