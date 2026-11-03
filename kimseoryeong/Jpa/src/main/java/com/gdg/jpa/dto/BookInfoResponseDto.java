package com.gdg.jpa.dto;

import com.gdg.jpa.domain.Book;
import lombok.Builder;

@Builder
public record BookInfoResponseDto(
        Long id,
        String title,
        int price,
        Long authorId,
        String authorName
) {

    public static BookInfoResponseDto from(Book book) {
        return BookInfoResponseDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .price(book.getPrice())
                .authorId(book.getAuthor().getId())
                .authorName(book.getAuthor().getAuthorName())
                .build();
    }
}
