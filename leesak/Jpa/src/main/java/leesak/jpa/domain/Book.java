package leesak.jpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 120)
    private String author;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;

    protected Book() {
    }

    public Book(String title, String author, Genre genre) {
        this.title = title;
        this.author = author;
        changeGenre(genre);
    }

    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public Genre getGenre() {
        return genre;
    }

    public void updateTitle(String title) {
        this.title = title;
    }
    public void updateAuthor(String author) {
        this.author = author;
    }

    public void changeGenre(Genre genre) {
        if (this.genre == genre) {
            return;
        }
        if (this.genre != null) {
            this.genre.getBooks().remove(this);
        }
        this.genre = genre;
        genre.getBooks().add(this);
    }
}
