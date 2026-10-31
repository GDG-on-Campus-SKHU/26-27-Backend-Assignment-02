package com.gdg.jpaexample.repository;

import com.gdg.jpaexample.domain.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
