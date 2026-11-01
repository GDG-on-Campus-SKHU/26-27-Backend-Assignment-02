package com.gdg.jpa.dto;

import com.gdg.jpa.domain.Author;
import lombok.Builder;

@Builder
public record AuthorInfoResponseDto(
        Long id,
        String authorName,
        String country
) {

    public static AuthorInfoResponseDto from(Author author) {
        return AuthorInfoResponseDto.builder()
                .id(author.getId())
                .authorName(author.getAuthorName())
                .country(author.getCountry())
                .build();
    }
}
