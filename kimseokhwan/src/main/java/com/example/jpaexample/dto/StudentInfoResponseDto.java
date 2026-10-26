package com.example.jpaexample.dto;

import com.example.jpaexample.domain.Student;
import lombok.Builder;

@Builder
public record StudentInfoResponseDto(
        Long id,
        String name,
        String major,
        Long courseId,
        String courseName
) {

    public static StudentInfoResponseDto from(Student student) {
        return StudentInfoResponseDto.builder()
                .id(student.getId())
                .name(student.getName())
                .major(student.getMajor())
                .courseId(student.getCourse().getId())
                .courseName(student.getCourse().getCourseName())
                .build();
    }
}