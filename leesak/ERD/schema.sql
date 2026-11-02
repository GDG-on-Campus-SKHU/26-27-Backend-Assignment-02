-- MySQL 기준: Book 및 Genre JPA 엔티티의 테이블 정의
CREATE TABLE genres (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE books (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    author VARCHAR(120) NOT NULL,
    genre_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_books_genre FOREIGN KEY (genre_id) REFERENCES genres (id)
);
