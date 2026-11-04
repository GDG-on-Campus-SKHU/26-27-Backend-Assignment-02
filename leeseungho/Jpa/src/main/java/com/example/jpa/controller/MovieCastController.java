package com.example.jpa.controller;

import com.example.jpa.dto.MovieCastInfoResponseDto;
import com.example.jpa.dto.MovieCastSaveRequestDto;
import com.example.jpa.dto.MovieCastUpdateRequestDto;
import com.example.jpa.service.MovieCastService;
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
@RequestMapping("/movieCasts")
public class MovieCastController {

    private final MovieCastService movieCastService;

    @PostMapping
    public ResponseEntity<MovieCastInfoResponseDto> saveMovieCast(@RequestBody MovieCastSaveRequestDto moviecastSaveRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieCastService.saveMovieCast(moviecastSaveRequestDto));
    }

    @GetMapping("/{movieCastId}")
    public ResponseEntity<MovieCastInfoResponseDto> getMovieCast(@PathVariable Long movieCastId) {
        return ResponseEntity.status(HttpStatus.OK).body(movieCastService.getMovieCast(movieCastId));
    }
    /*public ResponseEntity<MovieCastInfoResponseDto> getMovieCast(@PathVariable Long movieCastId) {
        return ResponseEntity.noContent().build();
    }

     */
    @PatchMapping("/{movieCastId}")
    public ResponseEntity<MovieCastInfoResponseDto> updateMovieCast(@PathVariable Long movieCastId,
                                                                @RequestBody MovieCastUpdateRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(movieCastService.updateMovieCast(movieCastId, requestDto));
    }

    @DeleteMapping("/{movieCastId}")
    public ResponseEntity<Void> deleteMovieCast(@PathVariable Long movieCastId) {
        movieCastService.deleteMovieCast(movieCastId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<MovieCastInfoResponseDto>> getAllMovieCast() {
        return ResponseEntity.status(HttpStatus.OK).body(movieCastService.getAllMovieCast());
    }
}
