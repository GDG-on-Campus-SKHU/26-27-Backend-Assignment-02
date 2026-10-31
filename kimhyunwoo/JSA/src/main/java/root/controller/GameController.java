package root.controller;

import root.dto.GameInfoResponseDto;
import root.dto.GameSaveRequestDto;
import root.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    @PostMapping
    public ResponseEntity<GameInfoResponseDto> saveGame(@RequestBody GameSaveRequestDto gameSaveRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gameService.saveGame(gameSaveRequestDto));
    }

    @DeleteMapping("/{gameId}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long gameId) {
        gameService.deleteGame(gameId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<GameInfoResponseDto>> getAllGame() {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.getAllGame());
    }
}
