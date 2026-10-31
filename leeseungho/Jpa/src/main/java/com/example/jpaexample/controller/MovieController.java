package com.example.jpaexample.controller;

import com.example.jpaexample.dto.MovieInfoResponseDto;
import com.example.jpaexample.dto.MovieSaveRequestDto;
import com.example.jpaexample.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<MovieInfoResponseDto> saveMovie(@RequestBody MovieSaveRequestDto movieSaveRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.saveMovie(movieSaveRequestDto));
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long movieId) {
        movieService.deleteMovie(movieId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<MovieInfoResponseDto>> getAllMovie() {
        return ResponseEntity.status(HttpStatus.OK).body(movieService.getAllMovie());
    }
}
