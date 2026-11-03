package com.gdg.jpa.domain;

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

@Entity
@Table(name = "books")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    @Builder
    public Book(String title, Integer price, Author author) {
        validatePrice(price);
        this.title = title;
        this.price = price;
        this.author = author;
    }

    public void update(String title, Integer price) {
        if (title != null) {
            this.title = title;
        }

        if (price != null) {
            validatePrice(price);
            this.price = price;
        }
    }

    private void validatePrice(Integer price) {
        if (price == null) {
            throw new IllegalArgumentException("가격을 입력해주세요.");
        }

        if (price < 0) {
            throw new IllegalArgumentException("가격은 0원 이상이어야 합니다.");
        }
    }
}
