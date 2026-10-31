package com.example.jpa.service;

import com.example.jpa.domain.Movie;
import com.example.jpa.dto.MovieInfoResponseDto;
import com.example.jpa.dto.MovieSaveRequestDto;
import com.example.jpa.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    @Transactional
    public MovieInfoResponseDto saveMovie(MovieSaveRequestDto movieSaveRequestDto) {
        Movie movie = Movie.builder()
                .movieName(movieSaveRequestDto.movieName())
                .editor(movieSaveRequestDto.editor())
                .build();

        movieRepository.save(movie);

        return MovieInfoResponseDto.from(movie);
    }

    @Transactional
    public void deleteMovie(Long movieId) {
        movieRepository.deleteById(movieId);
    }

    @Transactional(readOnly = true)
    public List<MovieInfoResponseDto> getAllMovie() {
        return movieRepository.findAll()
                .stream()
                .map(MovieInfoResponseDto::from)
                .toList();
    }
}
