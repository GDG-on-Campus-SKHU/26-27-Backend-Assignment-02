package com.example.jpaexample.service;

import com.example.jpaexample.domain.Author;
import com.example.jpaexample.dto.AuthorInfoResponseDto;
import com.example.jpaexample.dto.AuthorSaveRequestDto;
import com.example.jpaexample.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional
    public AuthorInfoResponseDto saveAuthor(AuthorSaveRequestDto authorSaveRequestDto) {
        Author author = Author.builder()
                .name(authorSaveRequestDto.name())
                .nationality(authorSaveRequestDto.nationality())
                .build();

        authorRepository.save(author);

        return AuthorInfoResponseDto.from(author);
    }

    @Transactional
    public void deleteAuthor(Long authorId) {
        authorRepository.deleteById(authorId);
    }

    @Transactional(readOnly = true)
    public List<AuthorInfoResponseDto> getAllAuthorAndBooks() {
        return authorRepository.findAll()
                .stream()
                .map(AuthorInfoResponseDto::from)
                .toList();
    }
}
