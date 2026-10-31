package com.example.jpaexample.controller;

import com.example.jpaexample.dto.ActorInfoResponseDto;
import com.example.jpaexample.dto.ActorSaveRequestDto;
import com.example.jpaexample.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/actors")
public class ActorController {

    private final ActorService actorService;

    @PostMapping
    public ResponseEntity<ActorInfoResponseDto> saveActor(@RequestBody ActorSaveRequestDto actorSaveRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(actorService.saveActor(actorSaveRequestDto));
    }

    @GetMapping("/{actorId}")
    public ResponseEntity<ActorInfoResponseDto> getActor(@PathVariable Long actorId) {
        return ResponseEntity.status(HttpStatus.OK).body(actorService.getActor(actorId));
    }

    @PatchMapping("/{actorId}")
    public ResponseEntity<ActorInfoResponseDto> updateActor(@PathVariable Long actorId,
                                                                @RequestBody ActorSaveRequestDto actorSaveRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(actorService.updateActor(actorId, actorSaveRequestDto));
    }

    @DeleteMapping("/{actorId}")
    public ResponseEntity<Void> deleteActor(@PathVariable Long actorId) {
        actorService.deleteActor(actorId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<ActorInfoResponseDto>> getAllActor() {
        return ResponseEntity.status(HttpStatus.OK).body(actorService.getAllActor());
    }
}
