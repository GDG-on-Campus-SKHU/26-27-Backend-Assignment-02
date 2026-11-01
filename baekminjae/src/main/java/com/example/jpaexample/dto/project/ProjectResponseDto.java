package com.example.jpaexample.dto.project;

import com.example.jpaexample.domain.Project;
import lombok.Builder;

@Builder
public record ProjectResponseDto(
        Long id,
        String projectTitle
) {
    public static ProjectResponseDto from(Project project) {
        return ProjectResponseDto.builder()
                .id(project.getId())
                .projectTitle(project.getProjectTitle())
                .build();
    }
}
