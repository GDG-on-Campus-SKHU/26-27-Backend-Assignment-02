package com.example.jpaexample.service;

import com.example.jpaexample.domain.Course;
import com.example.jpaexample.dto.CourseInfoResponseDto;
import com.example.jpaexample.dto.CourseSaveRequestDto;
import com.example.jpaexample.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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