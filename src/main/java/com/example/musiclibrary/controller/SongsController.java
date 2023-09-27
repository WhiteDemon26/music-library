package com.example.musiclibrary.controller;

import com.example.musiclibrary.service.MusicLibraryService;
import com.example.musiclibrary.model.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SongsController {


    @Autowired
    MusicLibraryService musicLibraryService;


    @GetMapping("/show_downloaded_songs")
    public ResponseEntity<List<Song>> showDownloadedSongs() {
        return new ResponseEntity<>(musicLibraryService.showDownloadedSongs(), HttpStatus.OK);
    }


    @GetMapping("/download_songs")
    public ResponseEntity<List<Song>> downloadSongs(@RequestBody List<Song> songs) {
        return new ResponseEntity<>(musicLibraryService.addSongs(songs), HttpStatus.OK);
    }


    @GetMapping("/find_songs_by_title/{songTitle}")
    public ResponseEntity<List<Song>> findSongsByTitle(@RequestParam String searchOnDB, @PathVariable String songTitle) {
        List<Song> songsFound = musicLibraryService.searchSongByTitle(searchOnDB, songTitle);
        return new ResponseEntity<>(songsFound, HttpStatus.OK);
    }


    @GetMapping("/find_songs_by_artist_name/{songArtistName}")
    public ResponseEntity<List<Song>> findSongsByArtist(@RequestParam String searchOnDB, @PathVariable String songArtistName) {
        List<Song> songsFound = musicLibraryService.searchSongByArtist(searchOnDB, songArtistName);
        return new ResponseEntity<>(songsFound, HttpStatus.OK);
    }


    @GetMapping("/find_songs_lyrics/{songLyrics}")
    public ResponseEntity<List<Song>> findSongsByLyrics(@RequestParam String searchOnDB, @PathVariable String songLyrics) {
        List<Song> songsFound = musicLibraryService.searchSongByLyrics(searchOnDB, songLyrics);
        return new ResponseEntity<>(songsFound, HttpStatus.OK);
    }


    @PostMapping("/select_songs")
    public ResponseEntity<String> selectedSongs(@RequestBody Integer[] ids) {
        String songsSelected = musicLibraryService.selectSongs(ids);
        return new ResponseEntity<>(songsSelected, HttpStatus.OK);
    }


    @GetMapping("/find_songs_most_played")
    public ResponseEntity<List<Song>> songsMostPlayed() {
        return new ResponseEntity<>(musicLibraryService.findMostPlayedSongs(), HttpStatus.OK);
    }


    @DeleteMapping("/delete_songs")
    public ResponseEntity<List<Song>> deleteSongs(){
        List<Song> songsDeleted = musicLibraryService.deleteSongs();
        return new ResponseEntity<>(songsDeleted, HttpStatus.OK);
    }
}

