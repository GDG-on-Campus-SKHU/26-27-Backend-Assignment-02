package root.dto;

import lombok.Builder;
import root.domain.SchoolClass;

@Builder
public record SchoolClassInfoResponseDto(
        Long id,
        String name,
        String major,
        Long courseId,
        String courseName
) {

    public static SchoolClassInfoResponseDto from(SchoolClass schoolClass) {
        return SchoolClassInfoResponseDto.builder()
                .id(schoolClass.getId())
                .name(schoolClass.getName())
                .major(schoolClass.getMajor())
                .courseId(schoolClass.getCourse().getId())
                .courseName(schoolClass.getCourse().getCourseName())
                .build();
    }
}
