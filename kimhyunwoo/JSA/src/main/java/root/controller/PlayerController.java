package root.controller;

import root.dto.PlayerInfoResponseDto;
import root.dto.PlayerSaveRequestDto;
import root.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping
    public ResponseEntity<PlayerInfoResponseDto> savePlayer(@RequestBody PlayerSaveRequestDto playerSaveRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(playerService.savePlayer(playerSaveRequestDto));
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<PlayerInfoResponseDto> getPlayer(@PathVariable Long playerId) {
        return ResponseEntity.status(HttpStatus.OK).body(playerService.getPlayer(playerId));
    }

    @PatchMapping("/{playerId}")
    public ResponseEntity<PlayerInfoResponseDto> updatePlayer(@PathVariable Long playerId,
                                                              @RequestBody PlayerSaveRequestDto playerSaveRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(playerService.updatePlayer(playerId, playerSaveRequestDto));
    }

    @DeleteMapping("/{playerId}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long playerId) {
        playerService.deletePlayer(playerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<PlayerInfoResponseDto>> getAllPlayer() {
        return ResponseEntity.status(HttpStatus.OK).body(playerService.getAllPlayer());
    }
}
