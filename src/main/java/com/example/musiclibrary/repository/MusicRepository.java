package com.example.musiclibrary.repository;

import com.example.musiclibrary.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MusicRepository extends JpaRepository<Song, Long> {

    List<Song> findBySongNameContaining(String title);
    List<Song> findByArtistContaining(String artistName);
    List<Song> findByLyricsContaining(String lyrics);
    List<Song> findByDownloaders_UserName(String userName);
    Optional<Song> findByUrl(String url);
}
