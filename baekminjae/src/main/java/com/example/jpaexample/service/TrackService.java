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

    // 트랙이 존재하는지 확인하기
    public Track validateTrackExists(Long trackId) {
        return trackRepository.findById(trackId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "존재하지 않는 트랙입니다."
                ));
    }

    private Track validateTrackInProject(Long projectId, Long trackId) {
        // 프로젝트와 트랙이 존재하는지 각각 확인하기
        Project project = projectService.validateProjectExists(projectId);
        Track track = validateTrackExists(trackId);

        // 트랙이 해당 프로젝트에 속하는지 확인하기
        if (!track.getProject().getId().equals(project.getId()))  {
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
        Project project = projectService.validateProjectExists(projectId);

        // Project 객체로 Track 객체 생성하기
        Track track = Track.builder()
                .project(project)
                .build();

        trackRepository.save(track);
        return TrackResponseDto.from(track);
    }

    @Transactional(readOnly = true)
    public List<TrackResponseDto> getAllTrack(Long projectId) {
        Project project = projectService.validateProjectExists(projectId);

        return trackRepository.findAll().stream()
                .filter(track -> track.getProject().getId().equals(project.getId()))
                .map(TrackResponseDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public TrackResponseDto getTrack(Long projectId, Long trackId) {
        Track track = validateTrackInProject(projectId, trackId);
        return TrackResponseDto.from(track);
    }

    @Transactional
    public TrackResponseDto updateTrack(Long projectId, Long trackId, TrackUpdateRequestDto request) {
        Track track = validateTrackInProject(projectId, trackId);

        if (request.gainDb() != null
                && (!Double.isFinite(request.gainDb()) || request.gainDb() < -60.0 || request.gainDb() > 12.0 )) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "볼륨 게인은 -60.0 ~ 12dB 사이여야 합니다.");
        }

        track.update(request.name(), request.gainDb(), request.muted());
        return TrackResponseDto.from(track);
    }

    @Transactional
    public void deleteTrack(Long projectId, Long trackId) {
        Track track = validateTrackInProject(projectId, trackId);
        trackRepository.deleteById(track.getId());
    }
}
