package leesak.jpa.dto;

import java.util.List;
import leesak.jpa.domain.Genre;

public record GenreResponse(Long id, String name, List<BookSummary> books) {
    public static GenreResponse from(Genre genre) {
        return new GenreResponse(genre.getId(), genre.getName(),
                genre.getBooks().stream().map(BookSummary::from).toList());
    }
}
