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
        try {
            return new ResponseEntity<>(musicLibraryService.showDownloadedSongs(), HttpStatus.OK);
        } catch (Exception e) {
            String message = "An error occurred while asking to see the downloaded songs !! \n Exception: " + e.getClass().getName() + ". Message: " + e.getMessage() + ". Cause: " + e.getCause();
            System.out.println(message);
            return null;
        }
    }


    @GetMapping("/upload_songs")
    public ResponseEntity<List<Song>> uploadSongs(@RequestBody List<Song> songs) {
        try {
            return new ResponseEntity<>(musicLibraryService.uploadSongs(songs), HttpStatus.OK);
        } catch (Exception e) {
            String message = "An error occurred while asking to add new songs !! \n Exception: " + e.getClass().getName() + ". Message: " + e.getMessage() + ". Cause: " + e.getCause();
            System.out.println(message);
            return null;
        }
    }


    @GetMapping("/download_songs")
    public ResponseEntity<List<Song>> downloadSongs(@RequestParam("ids") Long[] ids) {
        try {
            return new ResponseEntity<>(musicLibraryService.downloadSongs(ids), HttpStatus.OK);
        } catch (Exception e) {
            String message = "An error occurred while asking to add new songs !! \n Exception: " + e.getClass().getName() + ". Message: " + e.getMessage() + ". Cause: " + e.getCause();
            System.out.println(message);
            return null;
        }
    }


    @GetMapping("/find_songs_by_title/{songTitle}")
    public ResponseEntity<List<Song>> findSongsByTitle(@RequestParam("search_db") Boolean searchOnDB, @PathVariable String songTitle) {
        try {
            List<Song> songsFound = musicLibraryService.searchSongByTitle(searchOnDB, songTitle);
            return new ResponseEntity<>(songsFound, HttpStatus.OK);
        } catch (Exception e) {
            String message = "An error occurred while asking to see the songs by title !! \n Exception: " + e.getClass().getName() + ". Message: " + e.getMessage() + ". Cause: " + e.getCause();
            System.out.println(message);
            return null;
        }
    }


    @GetMapping("/find_songs_by_artist_name/{songArtistName}")
    public ResponseEntity<List<Song>> findSongsByArtist(@RequestParam("search_db") Boolean searchOnDB, @PathVariable String songArtistName) {
        try {
            List<Song> songsFound = musicLibraryService.searchSongByArtist(searchOnDB, songArtistName);
            return new ResponseEntity<>(songsFound, HttpStatus.OK);
        } catch (Exception e) {
            String message = "An error occurred while asking to see the songs by artist name !! \n Exception: " + e.getClass().getName() + ". Message: " + e.getMessage() + ". Cause: " + e.getCause();
            System.out.println(message);
            return null;
        }
    }


    @GetMapping("/find_songs_lyrics/{songLyrics}")
    public ResponseEntity<List<Song>> findSongsByLyrics(@RequestParam("search_db") Boolean searchOnDB, @PathVariable String songLyrics) {
        try {
            List<Song> songsFound = musicLibraryService.searchSongByLyrics(searchOnDB, songLyrics);
            return new ResponseEntity<>(songsFound, HttpStatus.OK);
        } catch (Exception e) {
            String message = "An error occurred while asking to see the songs by lyrics !! \n Exception: " + e.getClass().getName() + ". Message: " + e.getMessage() + ". Cause: " + e.getCause();
            System.out.println(message);
            return null;
        }
    }


    @PostMapping("/select_songs")
    public ResponseEntity<String> selectedSongs(@RequestParam("ids") Long[] ids) {
        try {
            String songsSelected = musicLibraryService.selectSongs(ids);
            return new ResponseEntity<>(songsSelected, HttpStatus.OK);
        } catch (Exception e) {
            String message = "An error occurred while asking to select the songs !! \n Exception: " + e.getClass().getName() + ". Message: " + e.getMessage() + ". Cause: " + e.getCause();
            System.out.println(message);
            return null;
        }
    }


    @GetMapping("/find_songs_most_played")
    public ResponseEntity<List<Song>> songsMostPlayed() {
        try {
            return new ResponseEntity<>(musicLibraryService.findMostPlayedSongs(), HttpStatus.OK);
        } catch (Exception e) {
            String message = "An error occurred while asking to see the most played songs !! \n Exception: " + e.getClass().getName() + ". Message: " + e.getMessage() + ". Cause: " + e.getCause();
            System.out.println(message);
            return null;
        }
    }


    @DeleteMapping("/delete_songs")
    public ResponseEntity<List<Song>> deleteSongs(@RequestParam("delete_DB_songs") Boolean deleteDBSongs) {
        try {
            List<Song> songsDeleted = musicLibraryService.deleteSongs(deleteDBSongs);
        return new ResponseEntity<>(songsDeleted, HttpStatus.OK);
        } catch (Exception e) {
            String message = "An error occurred while asking to delete your songs !! \n Exception: " + e.getClass().getName() + ". Message: " + e.getMessage() + ". Cause: " + e.getCause();
            System.out.println(message);
            return null;
        }
    }


    @GetMapping("/play_song/{song_id}")
    public ResponseEntity<Song> playSong(@PathVariable("song_id") Long id) {
        try {
            return new ResponseEntity<>(musicLibraryService.playSongSelectedFromFE(id), HttpStatus.OK);
        } catch (Exception e) {
            String message = "An error occurred while asking to play the song !! \n Exception: " + e.getClass().getName() + ". Message: " + e.getMessage() + ". Cause: " + e.getCause();
            System.out.println(message);
            return null;
        }
    }


    @GetMapping("/play_next_song")
    public ResponseEntity<Song> playNextSong() {
        try {
            return new ResponseEntity<>(musicLibraryService.nextSong(), HttpStatus.OK);
        } catch (Exception e) {
            String message = "An error occurred while asking to play the next song !! \n Exception: " + e.getClass().getName() + ". Message: " + e.getMessage() + ". Cause: " + e.getCause();
            System.out.println(message);
            return null;
        }
    }


    @GetMapping("/play_previous_song")
    public ResponseEntity<Song> playPreviousSong() {
        try {
            return new ResponseEntity<>(musicLibraryService.previousSong(), HttpStatus.OK);
        } catch (Exception e) {
            String message = "An error occurred while asking to play the previous song !! \n Exception: " + e.getClass().getName() + ". Message: " + e.getMessage() + ". Cause: " + e.getCause();
            System.out.println(message);
            return null;
        }
    }
}

