package com.example.jpaexample.domain;

import com.example.jpaexample.domain.Course;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "students")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String major;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Builder
    public Student(String name, String major, Course course) {
        this.name = name;
        this.major = major;
        this.course = course;
    }

    public void update(String name, String major) {
        this.name = name;
        this.major = major;
    }
}