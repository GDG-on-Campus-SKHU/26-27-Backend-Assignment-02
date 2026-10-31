package com.example.jpaexample.service;

import com.example.jpaexample.domain.Actor;
import com.example.jpaexample.domain.Movie;
import com.example.jpaexample.dto.ActorInfoResponseDto;
import com.example.jpaexample.dto.ActorSaveRequestDto;
import com.example.jpaexample.repository.ActorRepository;
import com.example.jpaexample.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActorService {

    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;

    @Transactional
    public ActorInfoResponseDto saveActor(ActorSaveRequestDto actorSaveRequestDto) {
        Movie movie = movieRepository.findById(actorSaveRequestDto.movieId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 영화입니다."));

        Actor actor = Actor.builder()
                .name(actorSaveRequestDto.name())
                .role(actorSaveRequestDto.role())
                .age(actorSaveRequestDto.age())
                .movie(movie)
                .build();

        actorRepository.save(actor);

        return ActorInfoResponseDto.from(actor);
    }

    @Transactional(readOnly = true)
    public ActorInfoResponseDto getActor(Long actorId) {
        Actor actor = actorRepository.findById(actorId)
                .orElseThrow(() -> new IllegalArgumentException("해당 배우에 대한 정보를 찾을 수 없습니다."));

        return ActorInfoResponseDto.from(actor);
    }

    @Transactional
    public ActorInfoResponseDto updateActor(Long actorId, ActorSaveRequestDto actorSaveRequestDto) {
        Actor actor = actorRepository.findById(actorId)
                .orElseThrow(() -> new IllegalArgumentException("해당 배우에 대한 정보를 찾을 수 없습니다."));

        actor.update(actorSaveRequestDto.name(),
                actorSaveRequestDto.role());

        return ActorInfoResponseDto.from(actor);
    }

    @Transactional
    public void deleteActor(Long actorId) {
        actorRepository.deleteById(actorId);
    }

    @Transactional(readOnly = true)
    public List<ActorInfoResponseDto> getAllActor() {
        return actorRepository.findAll()
                .stream()
                .map(ActorInfoResponseDto::from)
                .toList();
    }
}
