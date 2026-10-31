package com.gdg.gdg_be_assignment02.repository;

import com.gdg.gdg_be_assignment02.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
