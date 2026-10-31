package com.gdg.jpaexample.controller;

import com.gdg.jpaexample.dto.TrackInfoResponseDto;
import com.gdg.jpaexample.dto.TrackSaveRequestDto;
import com.gdg.jpaexample.sevice.TrackService;
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
@RequestMapping("/tracks")
public class TrackController {
    private final TrackService trackService;

    @PostMapping
    public ResponseEntity<TrackInfoResponseDto> saveTrack(@RequestBody TrackSaveRequestDto trackSaveRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trackService.saveTrack(trackSaveRequestDto));
    }

    @GetMapping("/{trackId}")
    public ResponseEntity<TrackInfoResponseDto> getTrack(@PathVariable Long trackId) {
        return ResponseEntity.status(HttpStatus.OK).body(trackService.getTrack(trackId));
    }

    @PatchMapping("/{trackId}")
    public ResponseEntity<TrackInfoResponseDto> updateTrack(@PathVariable Long trackId,
                                                                @RequestBody TrackSaveRequestDto trackSaveRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(trackService.updateTrack(trackId, trackSaveRequestDto));
    }

    @DeleteMapping("/{trackId}")
    public ResponseEntity<Void> deleteTrack(@PathVariable Long trackId) {
        trackService.deleteTrack(trackId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<TrackInfoResponseDto>> getAllTracks() {
        return ResponseEntity.status(HttpStatus.OK).body(trackService.getAllTracks());
    }
}
