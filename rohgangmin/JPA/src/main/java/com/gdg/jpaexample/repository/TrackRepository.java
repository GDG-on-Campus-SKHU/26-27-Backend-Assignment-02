package com.gdg.jpaexample.repository;

import com.gdg.jpaexample.domain.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, Long> {
}
