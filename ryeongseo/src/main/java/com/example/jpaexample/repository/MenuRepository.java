package com.example.jpaexample.repository;

import com.example.jpaexample.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
