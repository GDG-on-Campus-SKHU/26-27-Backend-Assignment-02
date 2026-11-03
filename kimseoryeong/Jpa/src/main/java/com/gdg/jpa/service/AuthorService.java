package com.gdg.jpa.service;

import com.gdg.jpa.domain.Author;
import com.gdg.jpa.dto.AuthorInfoResponseDto;
import com.gdg.jpa.dto.AuthorSaveRequestDto;
import com.gdg.jpa.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional
    public AuthorInfoResponseDto saveAuthor(AuthorSaveRequestDto authorSaveRequestDto) {
        Author author = Author.builder()
                .authorName(authorSaveRequestDto.authorName())
                .country(authorSaveRequestDto.country())
                .build();

        authorRepository.save(author);

        return AuthorInfoResponseDto.from(author);
    }

    @Transactional
    public void deleteAuthor(Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("삭제하려는 작가를 찾을 수 없습니다. id: " + authorId));

        if (!author.getBooks().isEmpty()) {
            throw new IllegalArgumentException("책이 남아 있는 작가는 삭제할 수 없습니다. 책을 먼저 삭제해주세요.");
        }

        authorRepository.delete(author);
    }

    @Transactional(readOnly = true)
    public List<AuthorInfoResponseDto> getAllAuthor() {
        List<Author> authors = authorRepository.findAll();
        List<AuthorInfoResponseDto> result = new ArrayList<>();

        for (Author author : authors) {
            result.add(AuthorInfoResponseDto.from(author));
        }

        return result;
    }
}
