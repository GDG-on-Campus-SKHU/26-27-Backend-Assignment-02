package leesak.jpa.service;

import java.util.List;
import leesak.jpa.domain.Genre;
import leesak.jpa.dto.GenreRequest;
import leesak.jpa.dto.GenreResponse;
import leesak.jpa.repository.BookRepository;
import leesak.jpa.repository.GenreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
public class GenreService {
    private final GenreRepository genres;
    private final BookRepository books;

    public GenreService(GenreRepository genres, BookRepository books) {
        this.genres = genres;
        this.books = books;
    }

    @Transactional
    public GenreResponse create(GenreRequest request) {
        Genre genre = new Genre(RequestValues.text(request.name(), "name", 100));
        return GenreResponse.from(genres.save(genre));
    }

    public List<GenreResponse> findAll() {
        return genres.findAll().stream().map(GenreResponse::from).toList();
    }

    public GenreResponse findById(Long id) {
        return GenreResponse.from(getGenre(id));
    }

    @Transactional
    public GenreResponse update(Long id, GenreRequest request) {
        Genre genre = getGenre(id);
        if (request.name() != null) {
            genre.updateName(RequestValues.text(request.name(), "name", 100));
        }
        return GenreResponse.from(genre);
    }

    @Transactional
    public void delete(Long id) {
        Genre genre = getGenre(id);
        if (books.existsByGenreId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "도서가 등록된 장르는 삭제할 수 없습니다. 도서를 삭제하거나 다른 장르로 옮겨주세요.");
        }
        genres.delete(genre);
    }

    private Genre getGenre(Long id) {
        return genres.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "장르를 찾을 수 없습니다: " + id));
    }
}
