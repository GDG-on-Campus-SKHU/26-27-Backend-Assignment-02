package com.example.jpa.service;


import com.example.jpa.domain.Category;
import com.example.jpa.dto.CategoryInfoResponseDto;
import com.example.jpa.dto.CategorySaveRequestDto;
import com.example.jpa.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryInfoResponseDto saveCategory(CategorySaveRequestDto categorySaveRequestDto) {
        Category category = Category.builder()
                .categoryName(categorySaveRequestDto.categoryName())
                .build();

        categoryRepository.save(category);

        return CategoryInfoResponseDto.from(category);
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다."));

        categoryRepository.delete(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryInfoResponseDto> getAllCategory() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryInfoResponseDto::from)
                .toList();
    }
}
