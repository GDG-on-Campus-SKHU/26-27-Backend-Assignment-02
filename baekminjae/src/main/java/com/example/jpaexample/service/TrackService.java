package com.example.jpaexample.service;

import com.example.jpaexample.domain.Project;
import com.example.jpaexample.domain.Track;
import com.example.jpaexample.dto.track.TrackResponseDto;
import com.example.jpaexample.dto.track.TrackUpdateRequestDto;
import com.example.jpaexample.repository.ProjectRepository;
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
    private final ProjectRepository projectRepository;

    @Transactional
    public TrackResponseDto saveTrack(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "존재하지 않는 프로젝트입니다."
                ));

        Track track = Track.builder()
                .project(project)
                .build();

        trackRepository.save(track);

        return TrackResponseDto.from(track);
    }

    @Transactional(readOnly = true)
    public List<TrackResponseDto> getAllTrack(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "존재하지 않는 프로젝트입니다."
                ));

        return trackRepository.findAll().stream()
                .filter(track -> track.getProject().getId().equals(project.getId()))
                .map(TrackResponseDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public TrackResponseDto getTrack(Long trackId) {
        Track track = trackRepository.findById(trackId)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "존재하지 않는 트랙입니다."
        ));

        return TrackResponseDto.from(track);
    }

    @Transactional
    public TrackResponseDto updateTrack(Long trackId, TrackUpdateRequestDto request) {
        Track track = trackRepository.findById(trackId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "존재하지 않는 트랙입니다."
                ));

        if (request.gainDb() != null
                && (!Double.isFinite(request.gainDb()) || request.gainDb() < -60.0 || request.gainDb() > 12.0 )) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "볼륨 게인은 -60.0 ~ 12dB 사이여야 합니다.");
        }

        track.update(request.name(), request.gainDb(), request.muted());

        return TrackResponseDto.from(track);
    }

    @Transactional
    public void deleteTrack(Long trackId) {
        trackRepository.deleteById(trackId);
    }
}
