package com.example.musiclibrary;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.example.musiclibrary.NewMLMain.returnFakeSongs;
import static java.lang.Thread.sleep;

public class MusicLibraryTest {



    public void getTimesPlayedSong() {
        MusicLibrary musicLibrary = new MusicLibrary();
        ArrayList<Song> returnMusic = musicLibrary.mostPlayed();
        for (Song song : returnMusic) {
            System.out.println(song.getSongName() + "has been played " + song.getTimesPlayed() + " times");
        }
    }



    public void checkIncreaseTimesPlayed() throws InterruptedException {

        ArrayList<Song> fakeSongs = new ArrayList<>();
        fakeSongs.addAll((returnFakeSongs()));
        MusicLibrary musicLibrary = new MusicLibrary();
        musicLibrary.setSongs(fakeSongs);

        Scanner modulo = new Scanner(System.in);
        System.out.println("select an title to listen: ");
        String title = modulo.nextLine();

        fakeSongs = musicLibrary.searchSongByTitle(title);
        Song firstSelectedSong = fakeSongs.get(0);

        musicLibrary.playSong(firstSelectedSong);
        System.out.println("number of times this song was played: " + firstSelectedSong.getTimesPlayed());

        System.out.println("");

        while (true) {

            System.out.println("Let's switch song!");
            musicLibrary.nextSong();
            System.out.println("now the playing song is: " + musicLibrary.getSongPlayingNow().getSongName());
            System.out.println("number of times that the song " + musicLibrary.getSongPlayingNow().getSongName() +
                    " played: " + musicLibrary.getSongPlayingNow().getTimesPlayed());

            sleep(4000);

            System.out.println("");
        }
    }

    public void showAllSongsOfArtist() {
        MusicLibrary musicLibrary = new MusicLibrary();
        List<Song> returnMusic = new ArrayList<>();
        returnMusic.addAll((returnFakeSongs()));

        while (true) {
            Scanner modulo = new Scanner(System.in);

            System.out.println("select an artist to listen: ");
            String artist = modulo.nextLine();

            returnMusic = musicLibrary.searchSongByArtist(artist);
            System.out.println(returnMusic.stream().map(song -> song.getSongName()).collect(Collectors.toList()));
        }
    }


    public void showAllReviews() {

        for (Review review : ReviewService.reviews) {
            System.out.println("Text of review: " + review.getText()
                    + " Stars of review: " + review.getStars()
                    + " Date and time of review: " + review.getSubmittedOnStringFormat());
        }
    }




    public void removeRandomSongs() throws InterruptedException {
        int range = 10;
        MusicLibrary musicLibrary = new MusicLibrary();
        ArrayList<Song> returnMusic = new ArrayList<>();
        returnMusic.addAll((returnFakeSongs()));

        while (true) {
            Random rand = new Random();
            int index = rand.nextInt(range);
            ArrayList<Song> songs = musicLibrary.getSongs();
            Integer[] numbers = new Integer[]{index};
            String message = musicLibrary.selectSongs(numbers);
            musicLibrary.deleteSongs();
            System.out.println("remaining songs: " + songs.size());
            sleep(3000);
            range--;
        }
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


    // test: in the while loop simulate the user selecting a song through a random number between 0 and N
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
