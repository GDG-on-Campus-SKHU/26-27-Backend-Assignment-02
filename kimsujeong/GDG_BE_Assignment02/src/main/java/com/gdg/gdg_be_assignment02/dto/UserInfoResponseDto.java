package com.gdg.gdg_be_assignment02.dto;

import com.gdg.gdg_be_assignment02.domain.User;
import lombok.Builder;

@Builder
public record UserInfoResponseDto(
        String name,
        int age
) {
    public static UserInfoResponseDto from(User user) { // from(): 객체를 응답용 DTO로 바꿔주는 메서드
        return UserInfoResponseDto.builder()
                .name(user.getName())
                .age(user.getAge())
                .build();
    }
}
