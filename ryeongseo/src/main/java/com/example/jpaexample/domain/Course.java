package com.example.jpaexample.domain;

import com.example.jpaexample.domain.Student;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String courseName;

    private String professor;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> students = new ArrayList<>();

    @Builder
    public Course(String courseName, String professor) {
        this.courseName = courseName;
        this.professor = professor;
    }
}