package root.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.domain.Course;
import root.domain.SchoolClass;
import root.dto.SchoolClassInfoResponseDto;
import root.dto.SchoolClassSaveRequestDto;
import root.repository.CourseRepository;
import root.repository.SchoolClassRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;
    private final CourseRepository courseRepository;

    @Transactional
    public SchoolClassInfoResponseDto saveSchoolClass(SchoolClassSaveRequestDto schoolClassSaveRequestDto) {
        Course course = courseRepository.findById(schoolClassSaveRequestDto.courseId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강의입니다."));

        SchoolClass schoolClass = SchoolClass.builder()
                .name(schoolClassSaveRequestDto.name())
                .major(schoolClassSaveRequestDto.major())
                .course(course)
                .build();

        schoolClassRepository.save(schoolClass);

        return SchoolClassInfoResponseDto.from(schoolClass);
    }

    @Transactional(readOnly = true)
    public SchoolClassInfoResponseDto getSchoolClass(Long schoolClassId) {
        SchoolClass schoolClass = schoolClassRepository.findById(schoolClassId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 수업 정보를 찾을 수 없습니다."));

        return SchoolClassInfoResponseDto.from(schoolClass);
    }

    @Transactional
    public SchoolClassInfoResponseDto updateSchoolClass(Long schoolClassId, SchoolClassSaveRequestDto schoolClassSaveRequestDto) {
        SchoolClass schoolClass = schoolClassRepository.findById(schoolClassId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 수업 정보를 찾을 수 없습니다."));

        schoolClass.update(
                schoolClassSaveRequestDto.name(),
                schoolClassSaveRequestDto.major()
        );

        return SchoolClassInfoResponseDto.from(schoolClass);
    }

    @Transactional
    public void deleteSchoolClass(Long schoolClassId) {
        schoolClassRepository.deleteById(schoolClassId);
    }

    @Transactional(readOnly = true)
    public List<SchoolClassInfoResponseDto> getAllSchoolClass() {
        return schoolClassRepository.findAll()
                .stream()
                .map(SchoolClassInfoResponseDto::from)
                .toList();
    }
}
