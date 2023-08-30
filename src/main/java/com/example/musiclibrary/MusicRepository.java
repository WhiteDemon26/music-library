package com.example.musiclibrary;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Song, Long> {
}
