package com.gdg.gdg_be_assignment02.controller;

import com.gdg.gdg_be_assignment02.dto.UserGetResponseDto;
import com.gdg.gdg_be_assignment02.dto.UserInfoResponseDto;
import com.gdg.gdg_be_assignment02.dto.UserSaveRequestDto;
import com.gdg.gdg_be_assignment02.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserInfoResponseDto> saveUser(@RequestBody UserSaveRequestDto userSaveRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userSaveRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserGetResponseDto> getUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<UserInfoResponseDto>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUser());
    }
}