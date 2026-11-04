package root.dto;

import lombok.Builder;
import root.domain.Player;

@Builder
public record PlayerInfoResponseDto(
        Long id,
        String name,
        String nickname,
        Long gameId,
        String gameName
) {
    public static PlayerInfoResponseDto from(Player player) {
        return PlayerInfoResponseDto.builder()
                .id(player.getId())
                .name(player.getName())
                .nickname(player.getNickname())
                .gameId(player.getGame().getId())
                .gameName(player.getGame().getGameName())
                .build();
    }
}
