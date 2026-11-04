package com.example.jpaexample.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String projectTitle;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private final List<Track> tracks = new ArrayList<>();

    @Builder
    public Project(String projectTitle) {
        if (!StringUtils.hasText(projectTitle)) {
            throw new IllegalArgumentException("프로젝트 제목은 비어 있을 수 없습니다.");
        }

        this.projectTitle = projectTitle;
    }

    public void rename(String projectTitle) {
        if (!StringUtils.hasText(projectTitle)) {
            throw new IllegalArgumentException("프로젝트 제목은 비어 있을 수 없습니다.");
        }

        this.projectTitle = projectTitle;
    }
}
