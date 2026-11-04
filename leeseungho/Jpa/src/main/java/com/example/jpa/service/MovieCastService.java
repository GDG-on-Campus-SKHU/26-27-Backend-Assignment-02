package com.example.jpa.service;

import com.example.jpa.domain.Movie;
import com.example.jpa.domain.MovieCast;
import com.example.jpa.dto.MovieCastInfoResponseDto;
import com.example.jpa.dto.MovieCastSaveRequestDto;
import com.example.jpa.dto.MovieCastUpdateRequestDto;
import com.example.jpa.repository.MovieCastRepository;
import com.example.jpa.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieCastService {

    private final MovieCastRepository movieCastRepository;
    private final MovieRepository movieRepository;

    private Movie findMovieById(Long movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 영화입니다."));
    }

    private MovieCast findMovieCastById(Long movieCastId) {
        return movieCastRepository.findById(movieCastId)
                .orElseThrow(() -> new IllegalArgumentException("해당 배우에 대한 정보를 찾을 수 없습니다."));
    }

    @Transactional
    public MovieCastInfoResponseDto saveMovieCast(MovieCastSaveRequestDto movieCastSaveRequestDto) {
        Movie movie = findMovieById(movieCastSaveRequestDto.movieId());

        MovieCast movieCast = MovieCast.builder()
                .name(movieCastSaveRequestDto.name())
                .role(movieCastSaveRequestDto.role())
                .age(movieCastSaveRequestDto.age())
                .movie(movie)
                .build();

        movieCastRepository.save(movieCast);

        return MovieCastInfoResponseDto.from(movieCast);
    }

    @Transactional(readOnly = true)
    public MovieCastInfoResponseDto getMovieCast(Long movieCastId) {
        MovieCast movieCast = findMovieCastById(movieCastId);
        return MovieCastInfoResponseDto.from(movieCast);
    }

    @Transactional
    public MovieCastInfoResponseDto updateMovieCast(Long movieCastId, MovieCastUpdateRequestDto requestDto) {
        MovieCast movieCast = findMovieCastById(movieCastId);

        Movie updateMovie = null;
        if (requestDto.movieId() != null) {
            updateMovie = findMovieById(requestDto.movieId());
        }

        movieCast.update(requestDto.name(), requestDto.role(), requestDto.age(), updateMovie);

        return MovieCastInfoResponseDto.from(movieCast);
    }

    @Transactional
    public void deleteMovieCast(Long movieCastId) {
        movieCastRepository.deleteById(movieCastId);
    }

    @Transactional(readOnly = true)
    public List<MovieCastInfoResponseDto> getAllMovieCast() {
        return movieCastRepository.findAll()
                .stream()
                .map(MovieCastInfoResponseDto::from)
                .toList();
    }
}
