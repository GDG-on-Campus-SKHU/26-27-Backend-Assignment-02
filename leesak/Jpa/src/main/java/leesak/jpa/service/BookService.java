package leesak.jpa.service;

import java.util.List;
import leesak.jpa.domain.Book;
import leesak.jpa.domain.Genre;
import leesak.jpa.dto.BookRequest;
import leesak.jpa.dto.BookResponse;
import leesak.jpa.repository.BookRepository;
import leesak.jpa.repository.GenreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository books;
    private final GenreRepository genres;

    public BookService(BookRepository books, GenreRepository genres) {
        this.books = books;
        this.genres = genres;
    }

    @Transactional
    public BookResponse create(BookRequest request) {
        String title = RequestValues.text(request.title(), "title", 200);
        String author = RequestValues.text(request.author(), "author", 120);
        Genre genre = getGenre(RequestValues.genreId(request.genreId()));
        return BookResponse.from(books.save(new Book(title, author, genre)));
    }

    public List<BookResponse> findAll() {
        return books.findAll().stream().map(BookResponse::from).toList();
    }

    public BookResponse findById(Long id) {
        return BookResponse.from(getBook(id));
    }

    @Transactional
    public BookResponse update(Long id, BookRequest request) {
        Book book = getBook(id);
        if (request.title() != null) {
            book.updateTitle(RequestValues.text(request.title(), "title", 200));
        }
        if (request.author() != null) {
            book.updateAuthor(RequestValues.text(request.author(), "author", 120));
        }
        if (request.genreId() != null) {
            book.changeGenre(getGenre(RequestValues.genreId(request.genreId())));
        }
        return BookResponse.from(book);
    }

    @Transactional
    public void delete(Long id) {
        books.delete(getBook(id));
    }

    private Book getBook(Long id) {
        return books.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "도서를 찾을 수 없습니다: " + id));
    }

    private Genre getGenre(Long id) {
        return genres.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "장르를 찾을 수 없습니다: " + id));
    }
}
