package com.example.jpaexample.repository;

import com.example.jpaexample.domain.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, Long> {
}
