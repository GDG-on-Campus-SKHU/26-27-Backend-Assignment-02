package com.example.jpaexample.repository;

import com.example.jpaexample.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}