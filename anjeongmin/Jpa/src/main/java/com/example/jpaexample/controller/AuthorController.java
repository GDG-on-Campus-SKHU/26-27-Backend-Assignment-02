package com.example.jpaexample.controller;

import com.example.jpaexample.dto.AuthorInfoResponseDto;
import com.example.jpaexample.dto.AuthorSaveRequestDto;
import com.example.jpaexample.service.AuthorService;
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
    public ResponseEntity<List<AuthorInfoResponseDto>> getAllAuthorAndBooks() {
        return ResponseEntity.status(HttpStatus.OK).body(authorService.getAllAuthorAndBooks());
    }
}
