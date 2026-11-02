package leesak.jpa.dto;

import leesak.jpa.domain.Book;

public record BookResponse(Long id, String title, String author, Long genreId, String genreName) {
    public static BookResponse from(Book book) {
        return new BookResponse(book.getId(), book.getTitle(), book.getAuthor(),
                book.getGenre().getId(), book.getGenre().getName());
    }
}
