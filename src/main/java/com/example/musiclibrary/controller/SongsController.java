package com.example.musiclibrary.controller;

import com.example.musiclibrary.MusicLibrary;
import com.example.musiclibrary.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SongsController {


    @Autowired
    MusicLibrary musicLibrary;


    @GetMapping("/show_downloaded_songs")
    public ResponseEntity<List<Song>> showDownloadedSongs() {
        String message = "you asked to see the downloaded songs (see this response's body) !!";
        System.out.println(message);
        return new ResponseEntity<>(musicLibrary.showDownloadedSongs(), HttpStatus.OK);
    }


    @GetMapping("/download_songs")
    public ResponseEntity<List<Song>> downloadSongs(@RequestBody List<Song> songs) {
        return new ResponseEntity<>(musicLibrary.addSongs(songs), HttpStatus.OK);
    }


    @PostMapping("/find_songs_by_title/{songTitle}")
    public ResponseEntity<Song> findSongsByTitle(@PathVariable String songTitle) {
        Song songsFound = musicLibrary.searchSongByTitle(songTitle).get(0);
        return new ResponseEntity<>(songsFound, HttpStatus.OK);
    }


    @PostMapping("/find_songs_by_artist_name/{songArtistName}")
    public ResponseEntity<List<Song>> findSongsByArtist(@PathVariable String songArtistName) {
        List<Song> songsFound = musicLibrary.searchSongByArtist(songArtistName);
        return new ResponseEntity<>(songsFound, HttpStatus.OK);
    }


    @PostMapping("/find_songs_lyrics/{songLyrics}")
    public ResponseEntity<List<Song>> findSongsByLyrics(@PathVariable String songLyrics) {
        List<Song> songsFound = musicLibrary.searchSongByLyrics(songLyrics);
        return new ResponseEntity<>(songsFound, HttpStatus.OK);
    }


    @PostMapping("/select_songs")
    public ResponseEntity<String> selectedSongs(@RequestBody Integer[] ids) {
        String songsSelected = musicLibrary.selectSongs(ids);
        return new ResponseEntity<>(songsSelected, HttpStatus.OK);
    }


    @GetMapping("/find_songs_most_played")
    public ResponseEntity<List<Song>> songsMostPlayed() {
        String message = "you asked to see the songs most played!!";
        System.out.println(message);
        return new ResponseEntity<>(musicLibrary.mostPlayed(), HttpStatus.OK);
    }


    @DeleteMapping("/delete_songs")
    public ResponseEntity<List<Song>> deleteSongs(){
        List<Song> songsDeleted = musicLibrary.deleteSongs();
        String message = "Your songs is deleted with success :) ";
        System.out.println(message);
        return new ResponseEntity<>(songsDeleted, HttpStatus.OK);
    }
}

