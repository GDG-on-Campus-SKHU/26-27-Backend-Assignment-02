package com.gdg.gdg_be_assignment02.dto;

import com.gdg.gdg_be_assignment02.domain.User;
import lombok.Builder;

import java.util.List;

@Builder
public record UserGetResponseDto( // 특정 유저와 해당 유저가 등록한 영화 목록 조회
        Long id,
        String name,
        int age,
        List<MovieInfoResponseDto> movies
) {
    public static UserGetResponseDto from(User user) {
        return UserGetResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .movies(user.getMovies()
                        .stream()
                        .map(MovieInfoResponseDto::from)
                        .toList())
                .build();
    }
}
