package com.example.musiclibrary.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity(name = "com.example.demo.raz.music_library.Song")
@Table(name = "song")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Data
@Getter
@Setter
//@EqualsAndHashCode
public class Song implements Comparable<Song> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false, length = 100, name = "song_name")
    private String songName;

    @Column(length = 100, name = "times_played")
    private int timesPlayed = 0;

    @Column(nullable = false, length = 100, name = "added_on")
    private LocalDateTime addedOn;

    @Column(nullable = false, length = 100, name = "added_on_string_format")
    private String addedOnStringFormat;

    @Column(length = 100)
    private String artist;

    @Column(length = 200)
    private String lyrics;

    @Column(name = "uploading_user", nullable = false)
    private String uploadingUser;

    @Transient
    private boolean selected = false;

    @Column(nullable = false, name = "length_seconds")
    private Long lengthSeconds = 0L;

    @Column(nullable = false, unique = true)
    private String url;


    // not in use
    @Transient
    private SongLength length;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "song_like",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> downloaders;


    @Override
    public int compareTo(Song o) {
        return o.timesPlayed - this.timesPlayed;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return getTimesPlayed() == song.getTimesPlayed() && isSelected() == song.isSelected() && Objects.equals(getId(), song.getId()) && Objects.equals(getSongName(), song.getSongName()) && Objects.equals(getAddedOn(), song.getAddedOn()) && Objects.equals(getAddedOnStringFormat(), song.getAddedOnStringFormat()) && Objects.equals(getArtist(), song.getArtist()) && Objects.equals(getLyrics(), song.getLyrics()) && Objects.equals(getLengthSeconds(), song.getLengthSeconds()) && Objects.equals(getLength(), song.getLength()) && Objects.equals(getDownloaders(), song.getDownloaders());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSongName(), getTimesPlayed(), getAddedOn(), getAddedOnStringFormat(), getArtist(), getLyrics(), isSelected(), getLengthSeconds(), getLength(), getDownloaders());
    }
}