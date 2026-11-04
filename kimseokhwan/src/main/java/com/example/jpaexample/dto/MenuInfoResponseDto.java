package com.example.jpaexample.dto;

import com.example.jpaexample.domain.Menu;
import lombok.Builder;

@Builder
public record MenuInfoResponseDto(
        Long id,
        String name,
        int price,
        Long categoryId,
        String categoryName
) {

    public static MenuInfoResponseDto from(Menu menu) {
        return MenuInfoResponseDto.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .categoryId(menu.getCategory().getId())
                .categoryName(menu.getCategory().getCategoryName())
                .build();
    }
}
