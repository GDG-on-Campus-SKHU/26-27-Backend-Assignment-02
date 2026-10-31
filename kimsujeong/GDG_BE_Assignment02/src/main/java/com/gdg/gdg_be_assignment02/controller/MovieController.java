package com.gdg.gdg_be_assignment02.controller;

import com.gdg.gdg_be_assignment02.dto.MovieInfoResponseDto;
import com.gdg.gdg_be_assignment02.dto.MovieSaveRequestDto;
import com.gdg.gdg_be_assignment02.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    @PostMapping("/{id}") // 경로 파라미터(id)로 전달받은 유저에 영화 정보 생성
    public ResponseEntity<MovieInfoResponseDto> saveMovie(@PathVariable Long id,
                                                          @RequestBody MovieSaveRequestDto movieSaveRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.saveMovie(id, movieSaveRequestDto));
    }

    @PatchMapping("/{movieId}")
    public ResponseEntity<MovieInfoResponseDto> updateMovie(@PathVariable Long movieId,
                                                            @RequestBody MovieSaveRequestDto movieSaveRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(movieService.updateMovie(movieId, movieSaveRequestDto));
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