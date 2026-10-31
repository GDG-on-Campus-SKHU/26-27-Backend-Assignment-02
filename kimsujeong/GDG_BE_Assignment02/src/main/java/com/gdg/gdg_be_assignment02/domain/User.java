package com.gdg.gdg_be_assignment02.domain;

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

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users") // 클래스 이름과 실제 테이블 이름이 다를 때, 어느 테이블과 연결할지 직접 적어주는 어노테이션
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동 생성
    private Long id;

    @Column(nullable = false)
    private String name; // 이름

    private int age; // 나이

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true) // 유저 한 명이 여러 영화를 참조
    private List<Movie> movies = new ArrayList<>();

    @Builder
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}