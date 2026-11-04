package com.example.jpa.service;


import com.example.jpa.domain.Category;
import com.example.jpa.domain.Menu;
import com.example.jpa.dto.MenuInfoResponseDto;
import com.example.jpa.dto.MenuSaveRequestDto;
import com.example.jpa.repository.CategoryRepository;
import com.example.jpa.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public MenuInfoResponseDto saveMenu(MenuSaveRequestDto menuSaveRequestDto) {
        Category category = categoryRepository.findById(menuSaveRequestDto.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        Menu menu = Menu.builder()
                .name(menuSaveRequestDto.name())
                .price(menuSaveRequestDto.price())
                .category(category)
                .build();

        menuRepository.save(menu);

        return MenuInfoResponseDto.from(menu);
    }

    @Transactional(readOnly = true)
    public MenuInfoResponseDto getMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 메뉴 정보를 찾을 수 없습니다."));

        return MenuInfoResponseDto.from(menu);
    }

    @Transactional
    public MenuInfoResponseDto updateMenu(Long menuId, MenuSaveRequestDto menuSaveRequestDto) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 메뉴 정보를 찾을 수 없습니다."));

        menu.update(menuSaveRequestDto.name(),
                menuSaveRequestDto.price());

        return MenuInfoResponseDto.from(menu);
    }

    @Transactional
    public void deleteMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));

        menuRepository.delete(menu);
    }

    @Transactional(readOnly = true)
    public List<MenuInfoResponseDto> getAllMenu() {
        return menuRepository.findAll()
                .stream()
                .map(MenuInfoResponseDto::from)
                .toList();
    }
}
