package com.gdg.gdg_be_assignment02.service;

import com.gdg.gdg_be_assignment02.domain.Movie;
import com.gdg.gdg_be_assignment02.domain.User;
import com.gdg.gdg_be_assignment02.dto.MovieInfoResponseDto;
import com.gdg.gdg_be_assignment02.dto.MovieSaveRequestDto;
import com.gdg.gdg_be_assignment02.repository.MovieRepository;
import com.gdg.gdg_be_assignment02.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    // saveMovie
    @Transactional
    public MovieInfoResponseDto saveMovie(Long userId, MovieSaveRequestDto movieSaveRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Movie movie = Movie.builder()
                .title(movieSaveRequestDto.title())
                .director(movieSaveRequestDto.director())
                .releaseDate(movieSaveRequestDto.releaseDate())
                .user(user)
                .build();

        movieRepository.save(movie);

        return MovieInfoResponseDto.from(movie);
    }

    // updateMovie
    @Transactional
    public MovieInfoResponseDto updateMovie(Long movieId, MovieSaveRequestDto movieSaveRequestDto) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException(("요청하신 영화 정보를 찾을 수 없습니다.")));

        movie.update(movieSaveRequestDto.title(), movieSaveRequestDto.director(), movieSaveRequestDto.releaseDate());

        return MovieInfoResponseDto.from(movie);
    }

    // deleteMovie
    @Transactional
    public void deleteMovie(Long movieId) {
        movieRepository.deleteById(movieId);
    }

    // getAllMovie
    @Transactional(readOnly = true)
    public List<MovieInfoResponseDto> getAllMovie() {
        return movieRepository.findAll()
                .stream()
                .map(MovieInfoResponseDto::from)
                .toList();
    }
}