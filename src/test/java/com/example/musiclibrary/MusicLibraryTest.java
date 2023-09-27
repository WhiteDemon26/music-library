package com.example.musiclibrary;

import com.example.musiclibrary.model.Review;
import com.example.musiclibrary.model.Song;
import com.example.musiclibrary.service.MusicLibraryService;
import com.example.musiclibrary.service.ReviewService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.example.musiclibrary.NewMLMain.returnFakeSongs;
import static java.lang.Thread.sleep;

public class MusicLibraryTest {



    public void getTimesPlayedSong() {
        MusicLibraryService musicLibraryService = new MusicLibraryService();
        ArrayList<Song> returnMusic = musicLibraryService.findMostPlayedSongs();
        for (Song song : returnMusic) {
            System.out.println(song.getSongName() + "has been played " + song.getTimesPlayed() + " times");
        }
    }



    public void checkIncreaseTimesPlayed() throws InterruptedException {

        List<Song> fakeSongs = new ArrayList<>();
        fakeSongs.addAll((returnFakeSongs()));
        MusicLibraryService musicLibraryService = new MusicLibraryService();
        musicLibraryService.setSongs(fakeSongs);

        Scanner modulo = new Scanner(System.in);
        System.out.println("select an title to listen: ");
        String title = modulo.nextLine();

        fakeSongs = musicLibraryService.searchSongByTitle(null, title);
        Song firstSelectedSong = fakeSongs.get(0);

        musicLibraryService.playSong(firstSelectedSong);
        System.out.println("number of times this song was played: " + firstSelectedSong.getTimesPlayed());

        System.out.println("");

        while (true) {

            System.out.println("Let's switch song!");
            musicLibraryService.nextSong();
            System.out.println("now the playing song is: " + musicLibraryService.getSongPlayingNow().getSongName());
            System.out.println("number of times that the song " + musicLibraryService.getSongPlayingNow().getSongName() +
                    " played: " + musicLibraryService.getSongPlayingNow().getTimesPlayed());

            sleep(4000);

            System.out.println("");
        }
    }

    public void showAllSongsOfArtist() {
        MusicLibraryService musicLibraryService = new MusicLibraryService();
        List<Song> returnMusic = new ArrayList<>();
        returnMusic.addAll((returnFakeSongs()));

        while (true) {
            Scanner modulo = new Scanner(System.in);

            System.out.println("select an artist to listen: ");
            String artist = modulo.nextLine();

            returnMusic = musicLibraryService.searchSongByArtist(null, artist);
            System.out.println(returnMusic.stream().map(song -> song.getSongName()).collect(Collectors.toList()));
        }
    }


    public void showAllReviews() {

        ReviewService reviewService = new ReviewService();
        for (Review review : reviewService.readReviews()) {
            System.out.println("Text of review: " + review.getText()
                    + " Stars of review: " + review.getStars()
                    + " Date and time of review: " + review.getSubmittedOnStringFormat());
        }
    }




    public void removeRandomSongs() throws InterruptedException {
        int range = 10;
        MusicLibraryService musicLibraryService = new MusicLibraryService();
        ArrayList<Song> returnMusic = new ArrayList<>();
        returnMusic.addAll((returnFakeSongs()));

        while (true) {
            Random rand = new Random();
            int index = rand.nextInt(range);
            List<Song> songs = musicLibraryService.getSongs();
            Integer[] numbers = new Integer[]{index};
            String message = musicLibraryService.selectSongs(numbers);
            musicLibraryService.deleteSongs();
            System.out.println("remaining songs: " + songs.size());
            sleep(3000);
            range--;
        }
    }


    public static ArrayList<Song> defaultSongs() {

        ArrayList<Song> defaultSongs = new ArrayList<>();
        Song song1 = Song.builder()
                .songName("Take Me Home, Country Roads")
                .artist("John Denver")
                .lyrics("Country roads, take me home\n" +
                        "To the place I belong\n" +
                        "West Virginia, mountain mama\n" +
                        "Take me home, country roads")
                .build();
        Song song2 = Song.builder()
                .songName("Wind Beneath My Wings")
                .artist("Bette Midler")
                .lyrics("So I was the one with all the glory\n" +
                        "While you were the one with all the strength\n" +
                        "A beautiful face without a name for so long\n" +
                        "A beautiful smile to hide the pain")
                .build();
        Song song3 = Song.builder()
                .songName("I Want It All")
                .artist("Queen")
                .lyrics("Gotta find me a future move out of my way\n" +
                        "I want it all, I want it all, I want it all, and I want it now\n" +
                        "I want it all, I want it all, I want it all, and I want it now")
                .build();
        defaultSongs.addAll(List.of(song1, song2, song3));

        return defaultSongs;
    }


/*









    // test: for each review show a message like:
    // "review number <index> (0, 1, 2, ecc.), stars : <number of stars>; is the review positive? true / false"


    SongLength songLength1 = new SongLength(0, 2, 2);
        System.out.println("first formatted length: " + songLength1.getFormattedLength());







    LocalDate localDate = LocalDate.now();
        System.out.println("LocalDate: " + localDate);


    LocalTime localTime = LocalTime.now();
        System.out.println("LocalTime: " + localTime);
        System.out.println("LocalTime: " + localTime.withNano(0));


    LocalDateTime now = LocalDateTime.now();
    // prints Saturday, 05 August 2023

        System.out.println("LocalDateTime: " + now);
        System.out.println("LocalDateTime.format(formatter): " + now.format(CUSTOM_FORMATTER));
        System.out.println("LocalDateTime.withNano(0):" + now.withNano(0));


    //01/02/2023


    // test: in the while loop simulate the user selecting a song through
    // a random number between 0 and N
    // suppose that number to be the index of the selected song
    // select the song and print the messages





        System.out.println(musicLibrary.reviewsOfUsers());

        musicLibrary.addReview(review1);
    //System.out.println("average of all the stars in the reviews: " + MusicLibrary.reviewAverage);

        musicLibrary.addReview(review2);
    //System.out.println("average of all the stars in the reviews: " + MusicLibrary.reviewAverage);

        musicLibrary.addReview(review3);
    //System.out.println("average of all the stars in the reviews: " + MusicLibrary.reviewAverage);

        musicLibrary.addReview(review4);
    //System.out.println("average of all the stars in the reviews: " + MusicLibrary.reviewAverage);

        musicLibrary.addReview(review5);
    //System.out.println("average of all the stars in the reviews: " + MusicLibrary.reviewAverage);

        musicLibrary.addReview(review6);
    //System.out.println("average of all the stars in the reviews: " + MusicLibrary.reviewAverage);











 */
}
