package com.example.jpaexample.controller;

import com.example.jpaexample.dto.track.TrackResponseDto;
import com.example.jpaexample.dto.track.TrackUpdateRequestDto;
import com.example.jpaexample.service.TrackService;
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
@RequestMapping("/projects/{projectId}/tracks")
public class TrackController {
    private final TrackService trackService;

    // 특정 프로젝트에 속하는 트랙 생성하기
    @PostMapping
    public ResponseEntity<TrackResponseDto> saveTrack(@PathVariable Long projectId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trackService.saveTrack(projectId));
    }

    // 특정 프로젝트에 속하는 모든 트랙 불러오기
    @GetMapping
    public ResponseEntity<List<TrackResponseDto>> getAllTrack(@PathVariable Long projectId) {
        return ResponseEntity.ok(trackService.getAllTrack(projectId));
    }

    // 특정 프로젝트에 속하는 특정 트랙 불러오기
    @GetMapping("/{trackId}")
    public ResponseEntity<TrackResponseDto> getTrack(
            @PathVariable Long trackId
    ) {
        return ResponseEntity.ok(trackService.getTrack(trackId));
    }

    // 특정 프로젝트에 속하는 특정 트랙 수정하기
    @PatchMapping("/{trackId}")
    public ResponseEntity<TrackResponseDto> updateTrack(
            @PathVariable Long trackId,
            @RequestBody TrackUpdateRequestDto request
    ) {
        return ResponseEntity.ok(trackService.updateTrack(trackId, request));
    }

    // 특정 프로젝트에 속하는 특정 트랙 삭제하기
    @DeleteMapping("/{trackId}")
    public ResponseEntity<Void> deleteTrack(@PathVariable Long trackId) {
        trackService.deleteTrack(trackId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
