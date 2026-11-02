package com.example.jpaexample.service;

import com.example.jpaexample.domain.Category;
import com.example.jpaexample.dto.CategoryInfoResponseDto;
import com.example.jpaexample.dto.CategorySaveRequestDto;
import com.example.jpaexample.repository.CategoryRepository;
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
        categoryRepository.deleteById(categoryId);
    }

    @Transactional(readOnly = true)
    public List<CategoryInfoResponseDto> getAllCategory() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryInfoResponseDto::from)
                .toList();
    }
}
