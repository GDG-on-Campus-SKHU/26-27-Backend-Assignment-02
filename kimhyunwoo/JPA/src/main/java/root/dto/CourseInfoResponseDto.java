package root.dto;

import lombok.Builder;
import root.domain.Course;

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
