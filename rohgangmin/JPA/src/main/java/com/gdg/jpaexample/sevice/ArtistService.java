package com.gdg.jpaexample.sevice;

import com.gdg.jpaexample.domain.Artist;
import com.gdg.jpaexample.dto.ArtistInfoResponseDto;
import com.gdg.jpaexample.dto.ArtistSaveRequestDto;
import com.gdg.jpaexample.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistRepository artistRepository;

    @Transactional
    public ArtistInfoResponseDto saveArtist(ArtistSaveRequestDto artistSaveRequestDto) {
        Artist artist = Artist.builder()
                .stageName(artistSaveRequestDto.stageName())
                .nationality(artistSaveRequestDto.nationality())
                .debutYear(artistSaveRequestDto.debutYear())
                .build();

        artistRepository.save(artist);

        return ArtistInfoResponseDto.from(artist);
    }

    @Transactional
    public void deleteArtist(Long artistId) {
        artistRepository.deleteById(artistId);
    }

    @Transactional(readOnly = true)
    public List<ArtistInfoResponseDto> getAllArtists() {
        return artistRepository.findAll()
                .stream()
                .map(ArtistInfoResponseDto::from)
                .toList();
    }
}
