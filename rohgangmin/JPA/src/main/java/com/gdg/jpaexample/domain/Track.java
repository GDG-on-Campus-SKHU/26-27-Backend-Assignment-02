package com.gdg.jpaexample.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "tracks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// 고유번샤

    @Column(nullable = false)
    private String title;// 노래 제목

    private String album;// 앨범 제목

    private LocalDate releaseDate;// 발매 날짜

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @Builder
    public Track(String title, String album, Artist artist, LocalDate releaseDate) {
        this.title = title;
        this.album = album;
        this.artist = artist;
        this.releaseDate = releaseDate;
    }

    public void update(String title, String album, LocalDate releaseDate) {
        this.title = title;
        this.album = album;
        this.releaseDate = releaseDate;
    }
}
