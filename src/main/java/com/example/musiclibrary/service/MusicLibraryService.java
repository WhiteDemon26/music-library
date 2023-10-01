package com.example.musiclibrary.service;

import com.example.musiclibrary.repository.MusicRepository;
import com.example.musiclibrary.model.Song;
import com.example.musiclibrary.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;
import static java.util.Comparator.comparing;

@Data
@Service
public class MusicLibraryService {

    public static final String APP_VERSION = "0.1";
    public final static DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private List<Song> songs = new ArrayList<>();

    // static serve a condividere a tutti gli utenti una determinata cosa che devono avere in comune che scelgo io di fargliela vedere e avere

    // private viene usata solo dentro l'istanza o dentro l'oggetto creato per essere chiamato fuori da questa classe bisogna chiamare il metodo getter
    private Song songPlayingNow;
    private Boolean isPlayingSongPaused;
    private ArrayList<Song> songsWithTimesPlayed;
    private ArrayList<User> friends;
    private int numberOfSelectedSongs;
    private boolean alwaysNextSongModeOn;
    private boolean alwaysPreviewSongModeOn;
    private boolean alwaysSameSongModeOn;
    private boolean alwaysRandomSongModeOn;

    @Autowired
    private MusicRepository musicRepository;

    @PostConstruct
    private void postConstruct() {
        this.songs = musicRepository.findAll();
        if(this.songs.isEmpty()) {
            addSongs(defaultSongs());
            System.out.println("You got some songs as a gift for downloading the app! Thank you for your support <3!");
        }
    }


    public void playNextSongModeOn() {
        this.alwaysNextSongModeOn = true;
        this.alwaysPreviewSongModeOn = false;
        this.alwaysSameSongModeOn = false;
        this.alwaysRandomSongModeOn = false;
    }

    public void playPreviewSongModeOn() {
        this.alwaysPreviewSongModeOn = true;
        this.alwaysNextSongModeOn = false;
        this.alwaysSameSongModeOn = false;
        this.alwaysRandomSongModeOn = false;
    }

    public void playSameSongModeOn() {
        this.alwaysSameSongModeOn = true;
        this.alwaysPreviewSongModeOn = false;
        this.alwaysNextSongModeOn = false;
        this.alwaysRandomSongModeOn = false;
    }

    public void playRandomSongModeOn() {
        this.alwaysRandomSongModeOn = true;
        this.alwaysNextSongModeOn = false;
        this.alwaysPreviewSongModeOn = false;
        this.alwaysSameSongModeOn = false;
    }


    private void setPlayingMode() throws InterruptedException{
        if(alwaysPreviewSongModeOn) {
            playIfAlwaysPreviewSongModeOn();
            return;
        }
        if(alwaysNextSongModeOn) {
            playIfAlwaysNextSongModeOn();
            return;
        }
        if(alwaysSameSongModeOn) {
            playIfAlwaysSameSongModeOn();
            return;
        }
        if(alwaysRandomSongModeOn) {
            playIfAlwaysRandomSongModeOn();
            return;
        }
    }


    public void playSong(Song song) throws InterruptedException {
        this.songPlayingNow = song;
        this.isPlayingSongPaused = false;
        setPlayingMode();
        play(song);
    }


    public void nextSong() throws InterruptedException {
        if(this.songPlayingNow == null) {
            return;
        }
        int index = songs.indexOf(this.songPlayingNow);
        if(index == songs.size()-1) {
            index = 0;
        } else {
            index = index + 1;
        }
        this.songPlayingNow = songs.get(index);
        setPlayingMode();
        play(this.songPlayingNow);
    }


    public void previousSong() throws InterruptedException {
        if(this.songPlayingNow == null) {
            return;
        }
        int index = songs.indexOf(this.songPlayingNow);
        if(index == 0) {
            index = songs.size()-1;
        } else {
            index = index -1;
        }
        this.songPlayingNow = songs.get(index);
        setPlayingMode();
        play(this.songPlayingNow);
    }

    private void playIfAlwaysRandomSongModeOn() throws InterruptedException {
        play(this.songPlayingNow);

        int range = songs.size();
        Random rand = new Random();
        while(true) {
            int randomIndex = rand.nextInt(range);
            play(songs.get(randomIndex));
        }
    }


    private void playIfAlwaysSameSongModeOn() throws InterruptedException {
        while(true) {
            play(this.songPlayingNow);
        }
    }


    private void playIfAlwaysPreviewSongModeOn() throws InterruptedException {
        int indexOfSongPlayingNow = this.songs.indexOf(this.songPlayingNow);
        for(int i = indexOfSongPlayingNow; i >= 0; i--) {
            play(songs.get(i));
            if (i == 0) {
                i = songs.size()-1;
            }
        }
    }


    private void playIfAlwaysNextSongModeOn() throws InterruptedException {
        int indexOfSongPlayingNow = this.songs.indexOf(this.songPlayingNow);
        for(int i = indexOfSongPlayingNow; i < songs.size(); i++) {
            play(songs.get(i));
            if (i == songs.size() - 1) {
                i = -1;
            }
        }
    }


    public List<Song> showDownloadedSongs() {
        // returns songs with its songs in order, ordered by submittedOn
        Collections.sort(songs, comparing(Song::getAddedOn).reversed());
        String message = "You asked to see the downloaded songs (see this response's body) !!";
        System.out.println(message);

        Song v = null;
        v.getSongName();

        return songs;
    }


    public static List<Song> defaultSongs() {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String path = System.getProperty("user.dir") + "\\src\\main\\resources\\default_songs.json";
            return objectMapper.readValue(new File(path), new TypeReference<List<Song>>(){});
        } catch (Exception e) {
            System.out.println("An error occurred while downloading the default songs !!" + e.getMessage());
            return Collections.emptyList();
        }
    }


    public String selectSongs(Integer[] indices) {
        // select is used to select and then to eliminate or share the song or songs
        String message;

        for(Integer indexSong : indices) {
            Song song = this.songs.get(indexSong);
            // if the song is not selected
            if (!song.isSelected()) {
                song.setSelected(true);
                this.numberOfSelectedSongs++;
                // if the song is not selected
            } else {
                song.setSelected(false);
                this.numberOfSelectedSongs--;
            }
        }
        if(this.numberOfSelectedSongs > 0) {
            message = this.numberOfSelectedSongs + " / " + this.songs.size();
        } else {
            message = "";
        }

        return message;
    }


    public List<Song> addSongs(List<Song> newSongs) {

        for (Song song : newSongs) {
            song.setAddedOn(LocalDateTime.now());
            song.setAddedOnStringFormat(LocalDateTime.now().format(CUSTOM_FORMATTER));
        }

        this.songs.addAll(newSongs);
        this.musicRepository.saveAll(newSongs);

        String message = "You added new songs (see this response's body) !!";
        System.out.println(message);
        return newSongs;
    }



    public List<Song> deleteSongs() {

        ArrayList<Song> songsCopy = new ArrayList<>(this.songs);

        Scanner scan = new Scanner(System.in);
        System.out.println("Do you want to delete the songs even from the DB? ");
        Boolean deleteDBSongs = scan.nextBoolean();

        for (Song song : songsCopy) {
            if (song.isSelected()) {
                this.songs.remove(song);
                if (deleteDBSongs) {
                    musicRepository.delete(song);
                    System.out.println("Your song " + song.getSongName() +" has been deleted from the DB.");
                }
            }
        }
        return songs;
    }


    private void play(Song song) throws InterruptedException {

        this.isPlayingSongPaused = false;

        song.setTimesPlayed(song.getTimesPlayed() + 1);
        System.out.println("the app is playing the song: " + song.getSongName());

        // integrate sleep to simulate the length of the song (just for testing, use only minutes as seconds)
        // TODO: to remove
        int minutes = song.getLength().getMinutes();
        int fakeSeconds = minutes * 1000;
        sleep(fakeSeconds);

        System.out.println("Song ended! ");
    }



    public void pauseSong() {
        this.isPlayingSongPaused = true;
        System.out.println("the playing song is paused now");
    }



    public List<Song> searchSongByTitle(String searchOnDB, String partOfTitle) {

        List<Song> returnMusic = new ArrayList<>();

        if(searchOnDB != null) {
            return this.musicRepository.findBySongNameContaining(partOfTitle);
        } else {
            for (Song song : this.songs) {
                String title = song.getSongName();
                // first way: use StringUtils.containsIgnoreCase(title, track)
                // second way: turn both the strings to upper case
                if (title.toUpperCase().contains(partOfTitle.toUpperCase())) {
                    returnMusic.add(song);
                }
            }
        }
        return returnMusic;
    }



    public List<Song> searchSongByArtist(String searchOnDB, String partOfArtistName) {

        List<Song> returnMusic = new ArrayList<>();

        if(searchOnDB != null) {
            return this.musicRepository.findByArtistContaining(partOfArtistName);
        } else {
            for (Song song : this.songs) {
                String artist = song.getArtist();
                if (artist.toUpperCase().contains(partOfArtistName.toUpperCase())) {
                    returnMusic.add(song);
                }
            }
        }
        return returnMusic;
    }


    public List<Song> searchSongByLyrics(String searchOnDB, String partOfLyrics) {

        List<Song> returnMusic = new ArrayList<>();

            if(searchOnDB != null) {
                return this.musicRepository.findByLyricsContaining(partOfLyrics);
            } else {
                for (Song song : this.songs) {
                    String lyrics = song.getLyrics();
                    if (lyrics.toUpperCase().contains(partOfLyrics.toUpperCase())) {
                        returnMusic.add(song);
                    }
                }
            }
        return returnMusic;
    }


    public ArrayList<Song> findMostPlayedSongs() {
        // ArrayList<Song> songsCopy = (ArrayList<Song>)this.songs.clone();
        // not valid anymore: songs is a List
        ArrayList<Song> songsCopy = new ArrayList<Song>(this.songs);

        // we could remove the songs that have never been listened to through:
        // songsCopy = new ArrayList<>(mostPlayedSongs.stream().filter(song -> song.timesPlayed != 0).collect(Collectors.toList()));
        // but java streams are used and you don't know them yet! Use method below

        // we copy the arraylist because we can't modify it while looping through it
        for (Song song : songs) {
            if (song.getTimesPlayed() == 0) {
                songsCopy.remove(song);
            }
        }

        // let's sort the songs by number of times they have been played ( comparing(etc.) )
        //Collections.sort(songsCopy, comparing(Song::getTimesPlayed).reversed());
        Collections.sort(songsCopy);
        String message = "You asked to see the most played songs !!";
        System.out.println(message);
        return songsCopy;
    }


    public Boolean shareSongsWithFewFriends(ArrayList<User> friends) {

        ArrayList<Song> songs4Friends = new ArrayList<>();

        for(Song song : this.songs) {
            if (song.isSelected()) {
                songs4Friends.add(song);
            }
            // the song is shared
            //System.out.println("song shared with your friends!!");
        }
        for ( User friend : friends) {
            // Boolean sentSuccessful = SendHttpRequest.send(songs4Friends , friend);
        }

        return true;
    }
}
