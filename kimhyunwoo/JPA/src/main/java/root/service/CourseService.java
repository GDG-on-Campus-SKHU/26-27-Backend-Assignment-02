package root.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.domain.Course;
import root.dto.CourseInfoResponseDto;
import root.dto.CourseSaveRequestDto;
import root.repository.CourseRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    @Transactional
    public CourseInfoResponseDto saveCourse(CourseSaveRequestDto courseSaveRequestDto) {
        Course course = Course.builder()
                .courseName(courseSaveRequestDto.courseName())
                .professor(courseSaveRequestDto.professor())
                .build();

        courseRepository.save(course);

        return CourseInfoResponseDto.from(course);
    }

    @Transactional
    public void deleteCourse(Long courseId) {
        courseRepository.deleteById(courseId);
    }

    @Transactional(readOnly = true)
    public List<CourseInfoResponseDto> getAllCourse() {
        return courseRepository.findAll()
                .stream()
                .map(CourseInfoResponseDto::from)
                .toList();
    }
}
