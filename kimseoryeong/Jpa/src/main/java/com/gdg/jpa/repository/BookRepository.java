package com.gdg.jpa.repository;

import com.gdg.jpa.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
