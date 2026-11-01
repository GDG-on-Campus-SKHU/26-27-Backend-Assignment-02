package com.gdg.jpa.controller;

import com.gdg.jpa.dto.BookInfoResponseDto;
import com.gdg.jpa.dto.BookSaveRequestDto;
import com.gdg.jpa.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookInfoResponseDto> saveBook(@RequestBody BookSaveRequestDto bookSaveRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.saveBook(bookSaveRequestDto));
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookInfoResponseDto> getBook(@PathVariable Long bookId) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getBook(bookId));
    }

    @PatchMapping("/{bookId}")
    public ResponseEntity<BookInfoResponseDto> updateBook(@PathVariable Long bookId,
                                                          @RequestBody BookSaveRequestDto bookSaveRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.updateBook(bookId, bookSaveRequestDto));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<BookInfoResponseDto>> getAllBook() {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllBook());
    }
}
