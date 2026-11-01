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
        authorRepository.deleteById(authorId);
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
