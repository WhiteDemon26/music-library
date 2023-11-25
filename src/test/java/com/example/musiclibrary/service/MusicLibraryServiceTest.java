package com.example.musiclibrary.service;

import com.example.musiclibrary.model.Song;
import com.example.musiclibrary.model.SongLength;
import com.example.musiclibrary.repository.MusicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.musiclibrary.NewMLMain.returnFakeSongs;
import static com.example.musiclibrary.service.MusicLibraryService.CUSTOM_FORMATTER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MusicLibraryServiceTest {

    @Mock
    MusicRepository musicRepository;

    @InjectMocks
    MusicLibraryService musicLibraryService;


    @Test
    void testPlaySongSelectedFromFE() throws InterruptedException {

        Song song = Song.builder()
                        .id(2L)
                        .artist("Enrique")
                        .songName("Bailando")
                        .timesPlayed(3)
                        .addedOn(LocalDateTime.now())
                        .lengthSeconds(120L)
                        .build();

        when(musicRepository.existsById(2L)).thenReturn(true);
        when(musicRepository.findById(2L)).thenReturn(Optional.of(song));
        Song songSelected = musicLibraryService.playSongSelectedFromFE(2L);
        assertEquals(songSelected.getTimesPlayed(), 4);

        when(musicRepository.existsById(2L)).thenReturn(false);
        Song songNotExists = musicLibraryService.playSongSelectedFromFE(2L);
        assertNull(songNotExists);
    }


    @Test
    void testPlaySong() throws InterruptedException {

        Song song = Song.builder()
                .id(2L)
                .artist("Enrique")
                .songName("Bailando")
                .timesPlayed(0)
                .addedOn(LocalDateTime.now())
                .lengthSeconds(120L)
                .build();

        Song songSelected = musicLibraryService.playSong(song);
        assertEquals(songSelected.getTimesPlayed(), 1);
        assertNotNull(musicLibraryService.getSongPlayingNow());
        assertFalse(musicLibraryService.getIsPlayingSongPaused());
    }


    //@Test
    void testNextSong() throws InterruptedException {

        Song song = Song.builder()
                        .id(2L)
                        .artist("Enrique")
                        .songName("Bailando")
                        .timesPlayed(0)
                        .addedOn(LocalDateTime.now())
                        .lengthSeconds(120L)
                        .build();

        Song songNotSelected = musicLibraryService.nextSong();
        assertNull(songNotSelected);

        musicLibraryService.setSongs(returnFakeSongs());
        musicLibraryService.setSongPlayingNow(song);

        Song songSelected = musicLibraryService.nextSong();
        assertEquals(songSelected.getTimesPlayed(), 21);
    }


    //@Test
    void testPreviousSong() throws InterruptedException {

        Song song = Song.builder()
                .artist("Enrique")
                .songName("Bailando")
                .timesPlayed(0)
                .addedOn(LocalDateTime.now())
                .lengthSeconds(120L)
                .build();

        Song songNotSelected = musicLibraryService.previousSong();
        assertNull(songNotSelected);

        musicLibraryService.setSongs(returnFakeSongs());
        musicLibraryService.setSongPlayingNow(song);

        Song songSelected = musicLibraryService.previousSong();
        assertNotNull(songSelected);
    }


    @Test
    void testShowDownloadedSongs() {

        musicLibraryService.setSongs(returnFakeSongs());
        List<Song> sortedSongs = musicLibraryService.showDownloadedSongs();

        for (int index = 0; index < musicLibraryService.getSongs().size() - 1; index++) {
            assertTrue( sortedSongs.get(index).getAddedOn().isAfter(sortedSongs.get(index+1).getAddedOn()) );
        }
    }


    @Test
    void testDefaultSongs() {

    }


    @Test
    void testSelectSongs() {

        Long[] ids = {1L, 2L, 3L, 4L};

        musicLibraryService.setSongs(returnFakeSongs());
        String songsSelected = musicLibraryService.selectSongs(ids);
        assertEquals(songsSelected, "4 / 10");

        int songSelected = musicLibraryService.getNumberOfSelectedSongs();
        assertEquals(songSelected,4);

        Long[] ids2 = {1L, 3L, 5L, 7L, 9L};
        String newSongsSelected = musicLibraryService.selectSongs(ids2);
        assertEquals(newSongsSelected, "5 / 10");
    }


    @Test
    void testAddSongs() {

        ArrayList<Song> mySongs = new ArrayList<>();

        Song song1 = Song.builder()
                        .id(1L)
                        .songName("Bailando")
                        .artist("Enrique Inglesias")
                        .lyrics("Yo te miro y se me corta la respiración\n" +
                                "Cuando tú me miras, se me sube el corazón\n" +
                                "(Me palpita lento el corazón)\n" +
                                "Y en un silencio tu mirada dice mil palabras (uh)\n" +
                                "La noche en la que te suplico que no salga el sol\n" +
                                "Bailando (bailando)\n")
                        .timesPlayed(20)
                        .addedOn(LocalDateTime.of(2010, 2, 2, 18, 9))
                        .addedOnStringFormat(LocalDateTime.of(2010, 2, 2, 18, 9).format(CUSTOM_FORMATTER))
                        .lengthSeconds(200L)
                        .length(new SongLength(0, 3, 47))
                        .build();
        Song song2 = Song.builder()
                        .id(2L)
                        .songName("Subeme La Radio")
                        .artist("Enrique Inglesias")
                        .lyrics("Súbeme la radio que esta es mi canción\n" +
                                "Siente el bajo que va subiendo\n" +
                                "Tráeme el alcohol que quita el dolor\n" +
                                "Hoy vamos a juntar la luna y el sol ")
                        .timesPlayed(23)
                        .addedOn(LocalDateTime.of(2020, 8, 26, 16, 30))
                        .addedOnStringFormat(LocalDateTime.of(2020, 8, 26, 16, 30).format(CUSTOM_FORMATTER))
                        .length(new SongLength(0, 3, 53))
                        .lengthSeconds(200L)
                        .build();
        mySongs.addAll(List.of(song1, song2));
        musicLibraryService.addSongs(mySongs);
        assertEquals(musicLibraryService.getSongs(), mySongs);

        assertNotNull(musicLibraryService.getSongs());
        verify(musicRepository).saveAll(any());
    }


    @Test
    void testDeleteSongs() {

        Song song1 = Song.builder()
                        .id(1L)
                        .songName("Bailando")
                        .artist("Enrique Inglesias")
                        .lyrics("Yo te miro y se me corta la respiración\n" +
                                "Cuando tú me miras, se me sube el corazón\n" +
                                "(Me palpita lento el corazón)\n" +
                                "Y en un silencio tu mirada dice mil palabras (uh)\n" +
                                "La noche en la que te suplico que no salga el sol\n" +
                                "Bailando (bailando)\n")
                        .timesPlayed(20)
                        .addedOn(LocalDateTime.of(2010, 2, 2, 18, 9))
                        .addedOnStringFormat(LocalDateTime.of(2010, 2, 2, 18, 9).format(CUSTOM_FORMATTER))
                        .lengthSeconds(200L)
                        .length(new SongLength(0, 3, 47))
                        .selected(true)
                        .build();
        Song song2 = Song.builder()
                        .id(2L)
                        .songName("Subeme La Radio")
                        .artist("Enrique Inglesias")
                        .lyrics("Súbeme la radio que esta es mi canción\n" +
                                "Siente el bajo que va subiendo\n" +
                                "Tráeme el alcohol que quita el dolor\n" +
                                "Hoy vamos a juntar la luna y el sol ")
                        .timesPlayed(23)
                        .addedOn(LocalDateTime.of(2020, 8, 26, 16, 30))
                        .addedOnStringFormat(LocalDateTime.of(2020, 8, 26, 16, 30).format(CUSTOM_FORMATTER))
                        .length(new SongLength(0, 3, 53))
                        .lengthSeconds(200L)
                        .build();
        List<Song> fakeSongs = new ArrayList<>(List.of(song1, song2));

        musicLibraryService.setSongs(fakeSongs);

        List<Song> deletedSongs = musicLibraryService.deleteSongs(true);
        assertEquals(1, deletedSongs.size());
        assertEquals("Bailando", deletedSongs.get(0).getSongName());
        verify(musicRepository).delete(any());
    }


    @Test
    void testSearchSongByTitle() {

        musicLibraryService.setSongs(returnFakeSongs());
        List<Song> foundSongLocal = musicLibraryService.searchSongByTitle(false, "Bailando");
        assertNotNull(foundSongLocal);
        assertEquals(foundSongLocal.size(), 1);


        when(musicRepository.findBySongNameContaining("Bailando")).thenReturn(List.of(musicLibraryService.getSongs().get(0)));
        List<Song> foundSongsFromDB = musicLibraryService.searchSongByTitle(true, "Bailando");
        verify(musicRepository).findBySongNameContaining(any());
        assertEquals(foundSongsFromDB.get(0).getSongName(),"Bailando");

        List<Song> foundSongDB = musicLibraryService.searchSongByTitle(false, "Xxxxxx");
        assertTrue(foundSongDB.isEmpty());
    }


    @Test
    void testSearchSongByArtist() {

        musicLibraryService.setSongs(returnFakeSongs());
        List<Song> foundSongLocal = musicLibraryService.searchSongByArtist(false, "Enrique");
        assertNotNull(foundSongLocal);
        assertEquals(foundSongLocal.size(), 3);

        when(musicRepository.findByArtistContaining("Enrique")).thenReturn(foundSongLocal);
        List<Song> foundSongsFromDB = musicLibraryService.searchSongByArtist(true, "Enrique");
        verify(musicRepository).findByArtistContaining(any());
        assertEquals(foundSongsFromDB.size(), 3);
        foundSongsFromDB.forEach(song -> assertTrue(song.getArtist().contains("Enrique Inglesias")));

        List<Song> notFoundSongDB = musicLibraryService.searchSongByArtist(false, "Xxxxxx");
        assertTrue(notFoundSongDB.isEmpty());
    }


    @Test
    void testSearchSongByLyrics() {

        musicLibraryService.setSongs(returnFakeSongs());
        List<Song> foundSongLocal = musicLibraryService.searchSongByLyrics(false, "Súbeme la radio");
        assertNotNull(foundSongLocal);
        assertEquals(foundSongLocal.size(), 1);

        when(musicRepository.findByLyricsContaining("Súbeme la radio")).thenReturn(List.of(musicLibraryService.getSongs().get(1)));
        List<Song> foundSongsFromDB = musicLibraryService.searchSongByLyrics(true, "Súbeme la radio");
        verify(musicRepository).findByLyricsContaining("Súbeme la radio");
        assertEquals(foundSongsFromDB.get(0).getSongName(),"Subeme La Radio");

        List<Song> notFoundSongDB = musicLibraryService.searchSongByLyrics(true, "Xxxxxx");
        assertTrue(notFoundSongDB.isEmpty());
    }


    @Test
    void testFindMostPlayedSongs() {

        musicLibraryService.setSongs(returnFakeSongs());
        List<Song> mostPlayedSongs = musicLibraryService.findMostPlayedSongs();

        for (int index = 0; index < mostPlayedSongs.size() - 1; index++) {
            assertTrue( mostPlayedSongs.get(index).getTimesPlayed() >= mostPlayedSongs.get(index+1).getTimesPlayed() );
        }
    }


    @Test
    void testShareSongsWithFewFriends() {
    }
}

