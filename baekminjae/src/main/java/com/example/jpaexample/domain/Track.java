package com.example.jpaexample.domain;

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

@Entity
@Table(name = "tracks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double gainDb;
    private Boolean muted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Builder
    public Track(Project project) {
        this.project = project;
        this.name = "track";
        this.gainDb = 0.0;
        this.muted = false;
    }

    public void update(String name, Double gainDb, Boolean muted) {
        if (name != null) {
            if  (name.isBlank()) {
                throw new IllegalArgumentException("트랙 제목은 비어 있을 수 없습니다.");
            }
            this.name = name;
        }
        if (gainDb != null) {
            if (!Double.isFinite(gainDb) || gainDb < -60.0 || gainDb > 12.0) {
                throw new IllegalArgumentException(
                        "볼륨 게인은 -60.0 ~ 12dB 사이여야 합니다."
                );
            }
            this.gainDb = gainDb;
        }
        if (muted != null) {
            this.muted = muted;
        }
    }
}
