package com.gdg.jpa.service;

import com.gdg.jpa.domain.Author;
import com.gdg.jpa.domain.Book;
import com.gdg.jpa.dto.BookInfoResponseDto;
import com.gdg.jpa.dto.BookSaveRequestDto;
import com.gdg.jpa.repository.AuthorRepository;
import com.gdg.jpa.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Transactional
    public BookInfoResponseDto saveBook(BookSaveRequestDto bookSaveRequestDto) {
        Author author = authorRepository.findById(bookSaveRequestDto.authorId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 작가입니다."));

        Book book = Book.builder()
                .title(bookSaveRequestDto.title())
                .price(bookSaveRequestDto.price())
                .author(author)
                .build();

        bookRepository.save(book);

        return BookInfoResponseDto.from(book);
    }

    @Transactional(readOnly = true)
    public BookInfoResponseDto getBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 책 정보를 찾을 수 없습니다."));

        return BookInfoResponseDto.from(book);
    }

    @Transactional
    public BookInfoResponseDto updateBook(Long bookId, BookSaveRequestDto bookSaveRequestDto) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 책 정보를 찾을 수 없습니다."));

        book.update(bookSaveRequestDto.title(),
                bookSaveRequestDto.price());

        return BookInfoResponseDto.from(book);
    }

    @Transactional
    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    @Transactional(readOnly = true)
    public List<BookInfoResponseDto> getAllBook() {
        List<Book> books = bookRepository.findAll();
        List<BookInfoResponseDto> result = new ArrayList<>();

        for (Book book : books) {
            result.add(BookInfoResponseDto.from(book));
        }

        return result;
    }
}
