package com.example.jpaexample.dto;

import com.example.jpaexample.domain.Book;
import com.example.jpaexample.domain.Genre;
import lombok.Builder;

@Builder
public record BookInfoResponseDto(
        Long id,
        String title,
        Genre genre,
        Long authorId,
        String authorName
) {

    public static BookInfoResponseDto from(Book book) {
        return BookInfoResponseDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .genre(book.getGenre())
                .authorId(book.getAuthor().getId())
                .authorName(book.getAuthor().getName())
                .build();
    }
}
