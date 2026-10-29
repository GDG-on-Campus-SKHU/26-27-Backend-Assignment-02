package root.dto;

public record SchoolClassSaveRequestDto(
        Long courseId,
        String name,
        String major
) {
}
