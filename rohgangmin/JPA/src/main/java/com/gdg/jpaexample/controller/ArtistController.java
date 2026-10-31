package com.gdg.jpaexample.controller;

import com.gdg.jpaexample.dto.ArtistInfoResponseDto;
import com.gdg.jpaexample.dto.ArtistSaveRequestDto;
import com.gdg.jpaexample.sevice.ArtistService;
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
@RequestMapping("/artists")
public class ArtistController {
    private final ArtistService artistService;

    @PostMapping
    public ResponseEntity<ArtistInfoResponseDto> saveCourse(@RequestBody ArtistSaveRequestDto artistSaveRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(artistService.saveArtist(artistSaveRequestDto));
    }

    @DeleteMapping("/{artistId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long artistId) {
        artistService.deleteArtist(artistId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<ArtistInfoResponseDto>> getAllArtists() {
        return ResponseEntity.status(HttpStatus.OK).body(artistService.getAllArtists());
    }
}
