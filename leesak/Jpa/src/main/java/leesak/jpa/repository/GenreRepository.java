package leesak.jpa.repository;

import java.util.List;
import java.util.Optional;
import leesak.jpa.domain.Genre;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Override
    @EntityGraph(attributePaths = "books")
    @Query("select g from Genre g order by g.id")
    List<Genre> findAll();

    @Override
    @EntityGraph(attributePaths = "books")
    Optional<Genre> findById(Long id);
}
