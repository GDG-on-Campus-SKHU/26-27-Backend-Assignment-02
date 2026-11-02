package com.example.jpaexample.controller;

import com.example.jpaexample.dto.CourseInfoResponseDto;
import com.example.jpaexample.dto.CourseSaveRequestDto;
import com.example.jpaexample.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseInfoResponseDto> saveCourse(@RequestBody CourseSaveRequestDto courseSaveRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.saveCourse(courseSaveRequestDto));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<CourseInfoResponseDto>> getAllCourse() {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getAllCourse());
    }
}