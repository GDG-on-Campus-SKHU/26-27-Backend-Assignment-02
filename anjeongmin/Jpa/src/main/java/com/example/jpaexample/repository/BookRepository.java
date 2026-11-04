package com.example.jpaexample.repository;

import com.example.jpaexample.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select b from Book b join fetch b.author")
    List<Book> findAllWithAuthor();
}
