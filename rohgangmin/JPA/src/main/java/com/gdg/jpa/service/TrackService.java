package com.gdg.jpa.service;

import com.gdg.jpa.domain.Artist;
import com.gdg.jpa.domain.Track;
import com.gdg.jpa.dto.TrackInfoResponseDto;
import com.gdg.jpa.dto.TrackSaveRequestDto;
import com.gdg.jpa.repository.ArtistRepository;
import com.gdg.jpa.repository.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackService {
    private final TrackRepository trackRepository;
    private final ArtistRepository artistRepository;

    @Transactional
    public TrackInfoResponseDto saveTrack(TrackSaveRequestDto trackSaveRequestDto) {
        Artist artist = artistRepository.findById(trackSaveRequestDto.artistId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 노래입니다."));

        Track track = Track.builder()
                .title(trackSaveRequestDto.title())
                .album(trackSaveRequestDto.album())
                .releaseDate(trackSaveRequestDto.releaseDate())
                .artist(artist)
                .build();

        trackRepository.save(track);

        return TrackInfoResponseDto.from(track);
    }

    @Transactional(readOnly = true)
    public TrackInfoResponseDto getTrack(Long trackId) {
        Track track = findTrackByIdOrThrow(trackId);

        return TrackInfoResponseDto.from(track);
    }

    @Transactional
    public TrackInfoResponseDto updateTrack(Long trackId, TrackSaveRequestDto trackSaveRequestDto) {
        Track track = findTrackByIdOrThrow(trackId);

        track.update(trackSaveRequestDto.title(),
                trackSaveRequestDto.album(), trackSaveRequestDto.releaseDate());

        return TrackInfoResponseDto.from(track);
    }

    @Transactional
    public void deleteTrack(Long trackId) {
        Track track = findTrackByIdOrThrow(trackId);
        trackRepository.delete(track);
    }

    @Transactional(readOnly = true)
    public List<TrackInfoResponseDto> getAllTracks() {
        return trackRepository.findAll()
                .stream()
                .map(TrackInfoResponseDto::from)
                .toList();
    }


    private Track findTrackByIdOrThrow(Long trackId) {
        return trackRepository.findById(trackId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 노래 정보를 찾을 수 없습니다."));
    }
}
