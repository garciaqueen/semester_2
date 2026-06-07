package org.example.music;

import org.example.auth.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistTest {
    @Test
    public void emptyPlaylistTest() {
        Playlist playlist = new Playlist();
        assertTrue(playlist.isEmpty());
    }

    @Test
    public void onePlaylistTest() {
        Playlist playlist = new Playlist();
        playlist.add(new Song("Nariia", "One two", 145));
        assertEquals(1, playlist.size());
    }

    @Test
    //1e
    public void isObjectTest() {
        Playlist playlist = new Playlist();
        Song newSong = new Song("Laufey", "Too Little, way too late", 160);
        playlist.add(newSong);
        assertEquals(newSong, playlist.get(0));
    }

    @Test
    // 1d
    public void shouldContainSameObject() {
        Playlist playlist = new Playlist();
        Song newSong = new Song("Laufey", "Too Little, way too late", 160);

        playlist.add(newSong);

        assertTrue(playlist.get(0) == newSong);
    }

    @Test
    // 1f
    public void isItCorrectSongTest() {
        Playlist playlist = new Playlist();
        Song song1 = new Song("Rihanna", "Breaking dishes", 145);
        Song song2 = new Song("Mitski", "I bet on Losing dogs", 150);
        Song song3 = new Song("Olivia Rodrigo", "The Cure", 130);
        playlist.add(song1);
        playlist.add(song2);
        playlist.add(song3);
        assertEquals(song2, playlist.atSecond(160));
    }

    @Test
    public void isItOutOfBonds() {
        Playlist playlist = new Playlist();
        Song song1 = new Song("Rihanna", "Breaking dishes", 145);
        playlist.add(song1);
        assertThrowsExactly(IndexOutOfBoundsException.class, () -> playlist.atSecond(150));
    }

    @Test
    //1h
    public void IsTheMessageCorrect() {
        Playlist playlist = new Playlist();
        Song song1 = new Song("Rihanna", "Breaking dishes", 145);
        playlist.add(song1);
        IndexOutOfBoundsException ex1 = assertThrows(IndexOutOfBoundsException.class, () -> playlist.atSecond(150));
        assertEquals("The number is too big for the playlist", ex1.getMessage());
        //assertThrowsExactly(IndexOutOfBoundsException.class, () -> playlist.atSecond(-5));
    }

    @Test
    //2b
    public void IsItFilled() throws Exception {
        Playlist playlist = new Playlist();
        Song.Persistance song1 = new Song.Persistance();
        Optional<Song> song = song1.read(0);
        song.ifPresent(playlist::add);
        assertFalse(playlist.isEmpty());
    }

    private Song.Persistance persistence;
    @BeforeEach
    public void setUp() throws Exception {
        persistence = new Song.Persistance();
        persistence.connect();
    }

    @AfterEach
    public void tearDown() throws Exception {
        persistence.disconnect();
    }
    @Test
    //2c
    public void isTheIndexWrong() throws Exception {
        Playlist playlist = new Playlist();
        Optional<Song> song = persistence.read(700);
        song.ifPresent(playlist::add);
        assertTrue(playlist.isEmpty());
    }


    static Stream<Arguments> songData() {
        return Stream.of(
                Arguments.of(0, "The Beatles", "Hey Jude"),
                Arguments.of(1, "The Rolling Stones", "(I Can't Get No) Satisfaction"),
                Arguments.of(700, null, null)
        );
    }

    @ParameterizedTest
    @MethodSource("songData")
    void testReadSongs(int index, String artist, String title) throws Exception {

        Song.Persistance persistence = new Song.Persistance();
        persistence.connect();

        Optional<Song> song = persistence.read(index);

        if (artist == null) {
            assertTrue(song.isEmpty());
        } else {
            assertTrue(song.isPresent());
            assertEquals(artist, song.get().artist());
            assertEquals(title, song.get().title());
        }

        persistence.disconnect();
    }

    static Stream<Arguments> songCsvData() throws IOException {
        File file = new File("src/main/resources/songs.csv");

        BufferedReader br = new BufferedReader(new FileReader(file));

        return br.lines()
                .skip(1) // skip header
                .map(line -> line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"))
                .map(parts -> Arguments.of(
                        Integer.parseInt(parts[0])-1,
                        parts[1],
                        parts[2]
                ));
    }

    @ParameterizedTest
    @MethodSource("songCsvData")
    void testReadSongsCsv(int index, String artist, String title) throws Exception {

        Song.Persistance persistence = new Song.Persistance();
        persistence.connect();

        Optional<Song> song = persistence.read(index);

        if (artist == null) {
            assertTrue(song.isEmpty());
        } else {
            assertTrue(song.isPresent());
            assertEquals(artist, song.get().artist());
            assertEquals(title, song.get().title());
        }

        persistence.disconnect();
    }

    @Test
    public void isItRegistered() throws SQLException {
        ListenerAccount.Persistence account;
        int generatedId = ListenerAccount.Persistence.register("mariia", "onetwo");
        assertTrue(generatedId > 0, "Generated Account ID should be a positive sequence index value.");

        int initialCredits = ListenerAccount.Persistence.getCredits(generatedId);

        assertEquals(0, initialCredits, "A newly registered ListenerAccount must start with exactly 0 credits.");

        assertFalse(ListenerAccount.Persistence.hasSong(generatedId, 101), "New users shouldn't have any songs linked to them yet.");
    }

}