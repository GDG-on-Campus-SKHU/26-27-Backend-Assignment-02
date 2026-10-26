package com.example.jpaexample.service;

import com.example.jpaexample.domain.Course;
import com.example.jpaexample.domain.Student;
import com.example.jpaexample.dto.StudentInfoResponseDto;
import com.example.jpaexample.dto.StudentSaveRequestDto;
import com.example.jpaexample.repository.CourseRepository;
import com.example.jpaexample.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Transactional
    public StudentInfoResponseDto saveStudent(StudentSaveRequestDto studentSaveRequestDto) {
        Course course = courseRepository.findById(studentSaveRequestDto.courseId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강의입니다."));

        Student student = Student.builder()
                .name(studentSaveRequestDto.name())
                .major(studentSaveRequestDto.major())
                .course(course)
                .build();

        studentRepository.save(student);

        return StudentInfoResponseDto.from(student);
    }

    @Transactional(readOnly = true)
    public StudentInfoResponseDto getStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 학생 정보를 찾을 수 없습니다."));

        return StudentInfoResponseDto.from(student);
    }

    @Transactional
    public StudentInfoResponseDto updateStudent(Long studentId, StudentSaveRequestDto studentSaveRequestDto) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 학생 정보를 찾을 수 없습니다."));

        student.update(studentSaveRequestDto.name(),
                studentSaveRequestDto.major());

        return StudentInfoResponseDto.from(student);
    }

    @Transactional
    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    @Transactional(readOnly = true)
    public List<StudentInfoResponseDto> getAllStudent() {
        return studentRepository.findAll()
                .stream()
                .map(StudentInfoResponseDto::from)
                .toList();
    }
}