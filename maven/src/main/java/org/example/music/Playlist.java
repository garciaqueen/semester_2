package org.example.music;

import java.util.ArrayList;

public class Playlist extends ArrayList<Song> {

    public Song atSecond(int number) {
        int seconds = 0;

        if (number < 0) {
            throw new IndexOutOfBoundsException("The number is too small");
        }
        int allSeconds = 0;
        for (Song song : this) {
            allSeconds += song.seconds();
        }
        if (number >= allSeconds) {
            throw new IndexOutOfBoundsException("The number is too big for the playlist");
        }
        for (Song song : this) {
            seconds += song.seconds();

            if (number < seconds) {
                return song;
            }
        }

        throw new IllegalArgumentException("No song at this second: " + number);
    }
}