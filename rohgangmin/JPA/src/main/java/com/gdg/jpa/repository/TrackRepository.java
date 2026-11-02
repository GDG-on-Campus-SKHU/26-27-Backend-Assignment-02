package com.gdg.jpa.repository;

import com.gdg.jpa.domain.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, Long> {
}
