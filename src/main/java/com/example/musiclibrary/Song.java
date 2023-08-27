package com.example.musiclibrary;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class Song implements Comparable<Song> {

    private String songName;
    private String artist;
    private String lyrics;
    private int timesPlayed = 0;
    private boolean selected = false;
    private LocalDateTime addedOn;
    private String addedOnStringFormat;
    private SongLength length;


    @Override
    public int compareTo(Song o) {
        return o.timesPlayed - this.timesPlayed;
    }
}