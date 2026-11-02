package com.example.jpaexample.controller;

import com.example.jpaexample.dto.StudentInfoResponseDto;
import com.example.jpaexample.dto.StudentSaveRequestDto;
import com.example.jpaexample.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentInfoResponseDto> saveStudent(@RequestBody StudentSaveRequestDto studentSaveRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.saveStudent(studentSaveRequestDto));
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentInfoResponseDto> getStudent(@PathVariable Long studentId) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getStudent(studentId));
    }

    @PatchMapping("/{studentId}")
    public ResponseEntity<StudentInfoResponseDto> updateStudent(@PathVariable Long studentId,
                                                                @RequestBody StudentSaveRequestDto studentSaveRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.updateStudent(studentId, studentSaveRequestDto));
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<StudentInfoResponseDto>> getAllStudent() {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getAllStudent());
    }
}