package leesak.jpa.dto;

import leesak.jpa.domain.Book;

public record BookSummary(Long id, String title, String author) {
    public static BookSummary from(Book book) {
        return new BookSummary(book.getId(), book.getTitle(), book.getAuthor());
    }
}
