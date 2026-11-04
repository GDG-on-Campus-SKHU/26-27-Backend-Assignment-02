package root.dto;

import lombok.Builder;
import root.domain.Game;

@Builder
public record GameInfoResponseDto(
        Long id,
        String gameName,
        String genre
) {
    public static GameInfoResponseDto from(Game game) {
        return GameInfoResponseDto.builder()
                .id(game.getId())
                .gameName(game.getGameName())
                .genre(game.getGenre())
                .build();
    }
}
