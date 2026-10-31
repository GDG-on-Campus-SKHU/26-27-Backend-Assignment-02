package root.dto;

public record PlayerSaveRequestDto(
        Long gameId,
        String name,
        String nickname
) {
}
