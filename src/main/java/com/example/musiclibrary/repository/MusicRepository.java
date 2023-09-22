package com.example.musiclibrary.repository;

import com.example.musiclibrary.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepository extends JpaRepository<Song, Long> {

    List<Song> findBySongNameContaining(String title);
}
