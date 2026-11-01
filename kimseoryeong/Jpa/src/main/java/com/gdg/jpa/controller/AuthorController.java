package com.gdg.jpa.controller;

import com.gdg.jpa.dto.AuthorInfoResponseDto;
import com.gdg.jpa.dto.AuthorSaveRequestDto;
import com.gdg.jpa.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorInfoResponseDto> saveAuthor(@RequestBody AuthorSaveRequestDto authorSaveRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.saveAuthor(authorSaveRequestDto));
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long authorId) {
        authorService.deleteAuthor(authorId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<AuthorInfoResponseDto>> getAllAuthor() {
        return ResponseEntity.status(HttpStatus.OK).body(authorService.getAllAuthor());
    }
}

