package com.gdg.jpaexample.sevice;

import com.gdg.jpaexample.domain.Artist;
import com.gdg.jpaexample.domain.Track;
import com.gdg.jpaexample.dto.TrackInfoResponseDto;
import com.gdg.jpaexample.dto.TrackSaveRequestDto;
import com.gdg.jpaexample.repository.ArtistRepository;
import com.gdg.jpaexample.repository.TrackRepository;
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
        Track track = trackRepository.findById(trackId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 노래 정보를 찾을 수 없습니다."));

        return TrackInfoResponseDto.from(track);
    }

    @Transactional
    public TrackInfoResponseDto updateTrack(Long trackId, TrackSaveRequestDto trackSaveRequestDto) {
        Track track = trackRepository.findById(trackId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 노래 정보를 찾을 수 없습니다."));

        track.update(trackSaveRequestDto.title(),
                trackSaveRequestDto.album(),trackSaveRequestDto.releaseDate());

        return TrackInfoResponseDto.from(track);
    }

    @Transactional
    public void deleteTrack(Long trackId) {
        trackRepository.deleteById(trackId);
    }

    @Transactional(readOnly = true)
    public List<TrackInfoResponseDto> getAllTracks() {
        return trackRepository.findAll()
                .stream()
                .map(TrackInfoResponseDto::from)
                .toList();
    }
}
