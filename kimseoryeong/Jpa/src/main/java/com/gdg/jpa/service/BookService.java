package com.gdg.jpa.service;

import com.gdg.jpa.domain.Author;
import com.gdg.jpa.domain.Book;
import com.gdg.jpa.dto.BookInfoResponseDto;
import com.gdg.jpa.dto.BookSaveRequestDto;
import com.gdg.jpa.dto.BookUpdateRequestDto;
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
        if (bookSaveRequestDto.authorId() == null) {
            throw new IllegalArgumentException("작가 id를 입력해주세요.");
        }

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
                .orElseThrow(() -> new IllegalArgumentException("조회하려는 책을 찾을 수 없습니다. id: " + bookId));

        return BookInfoResponseDto.from(book);
    }

    @Transactional
    public BookInfoResponseDto updateBook(Long bookId, BookUpdateRequestDto bookUpdateRequestDto) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("수정하려는 책을 찾을 수 없습니다. id: " + bookId));

        book.update(bookUpdateRequestDto.title(),
                bookUpdateRequestDto.price());

        return BookInfoResponseDto.from(book);
    }

    @Transactional
    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("삭제하려는 책을 찾을 수 없습니다. id: " + bookId));

        bookRepository.delete(book);
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
