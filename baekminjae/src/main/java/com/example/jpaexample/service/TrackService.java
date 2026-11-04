package com.example.jpaexample.service;

import com.example.jpaexample.domain.Project;
import com.example.jpaexample.domain.Track;
import com.example.jpaexample.dto.track.TrackResponseDto;
import com.example.jpaexample.dto.track.TrackUpdateRequestDto;
import com.example.jpaexample.repository.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackService {
    private final TrackRepository trackRepository;
    private final ProjectService projectService;

    // 트랙을 불러오고
    // 존재하지 않을 경우 예외처리 하기
    public Track getTrackOrThrow(Long trackId) {
        return trackRepository.findById(trackId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "존재하지 않는 트랙입니다."
                ));
    }

    private Track getTrackInProjectOrThrow(Long projectId, Long trackId) {
        Project project = projectService.getProjectOrThrow(projectId);
        Track track = getTrackOrThrow(trackId);

        // 트랙이 해당 프로젝트에 속하는지 확인하기
        if (!track.getProject().getId().equals(project.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "해당 프로젝트에 속한 트랙이 아닙니다."
            );
        }

        return track;
    }

    @Transactional
    public TrackResponseDto saveTrack(Long projectId) {
        // 트랙 id 로 Project 객체 확인하기
        Project project = projectService.getProjectOrThrow(projectId);

        // Project 객체로 Track 객체 생성하기
        Track track = Track.builder()
                .project(project)
                .build();

        trackRepository.save(track);
        return TrackResponseDto.from(track);
    }

    @Transactional(readOnly = true)
    public List<TrackResponseDto> getAllTrack(Long projectId) {
        Project project = projectService.getProjectOrThrow(projectId);

        return project.getTracks().stream()
                .map(TrackResponseDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public TrackResponseDto getTrack(Long projectId, Long trackId) {
        Track track = getTrackInProjectOrThrow(projectId, trackId);
        return TrackResponseDto.from(track);
    }

    @Transactional
    public TrackResponseDto updateTrack(Long projectId, Long trackId, TrackUpdateRequestDto request) {
        Track track = getTrackInProjectOrThrow(projectId, trackId);

        try {
            track.update(request.name(), request.gainDb(), request.muted());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage(),
                    e);
        }

        return TrackResponseDto.from(track);
    }

    @Transactional
    public void deleteTrack(Long projectId, Long trackId) {
        Track track = getTrackInProjectOrThrow(projectId, trackId);
        trackRepository.deleteById(track.getId());
    }
}
