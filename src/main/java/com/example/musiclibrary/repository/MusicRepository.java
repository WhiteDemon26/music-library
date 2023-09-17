package com.example.musiclibrary.repository;

import com.example.musiclibrary.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository extends JpaRepository<Song, Long> {
}
