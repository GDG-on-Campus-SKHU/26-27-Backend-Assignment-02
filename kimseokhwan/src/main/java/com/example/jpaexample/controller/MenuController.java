package com.example.jpaexample.controller;

import com.example.jpaexample.dto.MenuInfoResponseDto;
import com.example.jpaexample.dto.MenuSaveRequestDto;
import com.example.jpaexample.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menus")
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<MenuInfoResponseDto> saveMenu(@RequestBody MenuSaveRequestDto menuSaveRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(menuService.saveMenu(menuSaveRequestDto));
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<MenuInfoResponseDto> getMenu(@PathVariable Long menuId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(menuService.getMenu(menuId));
    }

    @PatchMapping("/{menuId}")
    public ResponseEntity<MenuInfoResponseDto> updateMenu(
            @PathVariable Long menuId,
            @RequestBody MenuSaveRequestDto menuSaveRequestDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(menuService.updateMenu(menuId, menuSaveRequestDto));
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long menuId) {
        menuService.deleteMenu(menuId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<MenuInfoResponseDto>> getAllMenu() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(menuService.getAllMenu());
    }
}
