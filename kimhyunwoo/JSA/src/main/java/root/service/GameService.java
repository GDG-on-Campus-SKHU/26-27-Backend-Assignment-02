package root.service;

import root.domain.Game;
import root.dto.GameInfoResponseDto;
import root.dto.GameSaveRequestDto;
import root.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    @Transactional
    public GameInfoResponseDto saveGame(GameSaveRequestDto gameSaveRequestDto) {
        Game game = Game.builder()
                .gameName(gameSaveRequestDto.gameName())
                .genre(gameSaveRequestDto.genre())
                .build();

        gameRepository.save(game);

        return GameInfoResponseDto.from(game);
    }

    @Transactional
    public void deleteGame(Long gameId) {
        gameRepository.deleteById(gameId);
    }

    @Transactional(readOnly = true)
    public List<GameInfoResponseDto> getAllGame() {
        return gameRepository.findAll()
                .stream()
                .map(GameInfoResponseDto::from)
                .toList();
    }
}
