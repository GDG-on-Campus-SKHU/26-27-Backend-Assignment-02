package com.example.jpaexample.repository;

import com.example.jpaexample.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
