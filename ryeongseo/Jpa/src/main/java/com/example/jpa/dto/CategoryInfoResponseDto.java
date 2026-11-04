package com.example.jpa.dto;


import com.example.jpa.domain.Category;
import lombok.Builder;

@Builder
public record CategoryInfoResponseDto(
        Long id,
        String categoryName
) {

    public static CategoryInfoResponseDto from(Category category) {
        return CategoryInfoResponseDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .build();
    }
}
