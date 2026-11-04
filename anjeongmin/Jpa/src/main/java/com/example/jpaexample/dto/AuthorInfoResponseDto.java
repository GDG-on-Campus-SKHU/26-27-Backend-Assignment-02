package com.example.jpaexample.dto;

import com.example.jpaexample.domain.Author;
import lombok.Builder;

import java.util.List;

@Builder
public record AuthorInfoResponseDto(
        Long id,
        String name,
        String nationality,
        List<BookInfoResponseDto> books
) {
    public static AuthorInfoResponseDto from(Author author) {
        List<BookInfoResponseDto> books = author.getBooks().stream()
                .map(BookInfoResponseDto::from)
                .toList();

        return AuthorInfoResponseDto.builder()
                .id(author.getId())
                .name(author.getName())
                .nationality(author.getNationality())
                .books(books)
                .build();
    }
}
