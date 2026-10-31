package com.gdg.gdg_be_assignment02.domain;

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
@Table(name = "movies")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    @Column(nullable = false)
    private String title; // 제목

    private String director; // 감독
    private LocalDate releaseDate; // 개봉일: LocalDate 타입

    @ManyToOne(fetch = FetchType.LAZY) // 영화 여러 개가 유저 한 명을 참조
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Movie(String title, String director, LocalDate releaseDate, User user) {
        this.title = title;
        this.director = director;
        this.releaseDate = releaseDate;
        this.user = user;
    }

    public void update(String title, String director, LocalDate releaseDate) {
        this.title = title;
        this.director = director;
        this.releaseDate = releaseDate;
    }
}