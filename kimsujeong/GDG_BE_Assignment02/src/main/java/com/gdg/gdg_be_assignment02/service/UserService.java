package com.gdg.gdg_be_assignment02.service;

import com.gdg.gdg_be_assignment02.domain.User;
import com.gdg.gdg_be_assignment02.dto.UserGetResponseDto;
import com.gdg.gdg_be_assignment02.dto.UserInfoResponseDto;
import com.gdg.gdg_be_assignment02.dto.UserSaveRequestDto;
import com.gdg.gdg_be_assignment02.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // saveUser
    @Transactional
    public UserInfoResponseDto saveUser(UserSaveRequestDto userSaveRequestDto) {
        User user = User.builder()
                .name(userSaveRequestDto.name())
                .age(userSaveRequestDto.age())
                .build();

        userRepository.save(user);

        return UserInfoResponseDto.from(user);
    }

    // getUser - 특정 유저를 찾는 작업
    @Transactional(readOnly = true)
    public UserGetResponseDto getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 유저 정보를 찾을 수 없습니다."));

        return UserGetResponseDto.from(user);
    }

    // deleteUser
    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    // getAllUser
    @Transactional(readOnly = true)
    public List<UserInfoResponseDto> getAllUser() {
        return userRepository.findAll()
                .stream()
                .map(UserInfoResponseDto::from)
                .toList();
    }
}