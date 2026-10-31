package root.service;

import root.domain.Game;
import root.domain.Player;
import root.repository.GameRepository;
import root.repository.GameRepository;
import root.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.dto.PlayerInfoResponseDto;
import root.dto.PlayerSaveRequestDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;

    @Transactional
    public PlayerInfoResponseDto savePlayer(PlayerSaveRequestDto playerSaveRequestDto) {
        Game game = gameRepository.findById(playerSaveRequestDto.gameId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다."));

        Player player = Player.builder()
                .name(playerSaveRequestDto.name())
                .nickname(playerSaveRequestDto.nickname())
                .game(game)
                .build();

        playerRepository.save(player);

        return PlayerInfoResponseDto.from(player);
    }

    @Transactional(readOnly = true)
    public PlayerInfoResponseDto getPlayer(Long playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 플레이어 정보를 찾을 수 없습니다."));

        return PlayerInfoResponseDto.from(player);
    }

    @Transactional
    public PlayerInfoResponseDto updatePlayer(Long playerId, PlayerSaveRequestDto playerSaveRequestDto) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 플레이어 정보를 찾을 수 없습니다."));

        player.update(playerSaveRequestDto.name(),
                playerSaveRequestDto.nickname());

        return PlayerInfoResponseDto.from(player);
    }

    @Transactional
    public void deletePlayer(Long playerId) {
        playerRepository.deleteById(playerId);
    }

    @Transactional(readOnly = true)
    public List<PlayerInfoResponseDto> getAllPlayer() {
        return playerRepository.findAll()
                .stream()
                .map(PlayerInfoResponseDto::from)
                .toList();
    }
}
