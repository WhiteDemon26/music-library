package com.example.musiclibrary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "com.example.demo.raz.music_library.Song")
@Table(name = "song")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Song implements Comparable<Song> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false, length = 100, name = "song_name")
    private String songName;

    @Column(nullable = false, length = 100, name = "times_played")
    private int timesPlayed = 0;

    @Column(nullable = false, length = 100, name = "added_on")
    private LocalDateTime addedOn;

    @Column(nullable = false, length = 100, name = "added_on_string_format")
    private String addedOnStringFormat;

    @Column(nullable = false, length = 100)
    private String artist;

    @Column(length = 100)
    private String lyrics;

    @Transient
    private boolean selected = false;

    @Transient
    private SongLength length;


    @Override
    public int compareTo(Song o) {
        return o.timesPlayed - this.timesPlayed;
    }
}