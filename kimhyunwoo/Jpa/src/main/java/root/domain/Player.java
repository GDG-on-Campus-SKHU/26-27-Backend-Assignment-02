package root.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "players")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String nickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @Builder
    public Player(String name, String nickname, Game game) {
        this.name = name;
        this.nickname = nickname;
        this.game = game;
    }

    public void update(String name, String nickname) {
        this.name = name;
        this.nickname = nickname;
    }
}
