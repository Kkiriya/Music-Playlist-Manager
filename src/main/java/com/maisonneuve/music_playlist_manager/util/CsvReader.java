package com.maisonneuve.music_playlist_manager.util;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.maisonneuve.music_playlist_manager.model.Genre;
import com.maisonneuve.music_playlist_manager.model.Song;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

// Based on:
// https://howtodoinjava.com/java/io/parse-csv-files-in-java/
// https://howtodoinjava.com/java/io/inputstream-to-string/

public class CsvReader {
    private static final String SONGS_PATH = "/com/maisonneuve/music_playlist_manager/data/songs.csv";

    /**
     * Reads the songs CSV and returns the songs found in it.
     *
     * @return the list of songs from the CSV
     */
    public List<Song> readSongs() {
        List<Song> songs = new ArrayList<>();

        try (InputStream inputStream = CsvReader.class.getResourceAsStream(SONGS_PATH)) {
            if (inputStream == null) {
                return songs;
            }

            try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                reader.readNext();

                String[] nextLine;
                while ((nextLine = reader.readNext()) != null) {
                    if (nextLine.length < 12) {
                        continue;
                    }

                    songs.add(toSong(nextLine));
                }
            }
        } catch (IOException | CsvValidationException | NumberFormatException exception) {
            throw new IllegalStateException("Impossible de lire le fichier CSV des chansons.", exception);
        }

        return songs;
    }

    /**
     * Builds one Song from one CSV row.
     */
    private Song toSong(String[] columns) {
        String songId = columns[0];
        String title = columns[1];
        String artist = columns[3];
        Genre genre = parseGenre(columns[4]);
        String album = columns[6];
        String releaseDate = columns[7];
        int duration = parseDurationInSeconds(columns[10]);
        int listenCount = Integer.parseInt(columns[11]);

        return new Song();//songId, title, artist, album, releaseDate, genre, duration, listenCount);
    }

    /**
     * Converts the CSV duration from minutes to seconds.
     */
    private int parseDurationInSeconds(String durationValue) {
        double minutes = Double.parseDouble(durationValue);
        return (int) Math.round(minutes * 60);
    }

    /**
     * Maps the Spotify genre text to one of our enum values.
     */
    private Genre parseGenre(String value) {
        String genre = value.toLowerCase();

        if (genre.contains("rock") || genre.contains("metal")) {
            return Genre.ROCK;
        }
        if (genre.contains("hip") || genre.contains("rap") || genre.contains("trap")) {
            return Genre.HIP_HOP;
        }
        if (genre.contains("jazz")) {
            return Genre.JAZZ;
        }
        if (genre.contains("classical") || genre.contains("classic")) {
            return Genre.CLASSICAL;
        }
        if (genre.contains("electronic") || genre.contains("edm") || genre.contains("house") || genre.contains("techno")) {
            return Genre.ELECTRONIC;
        }

        return Genre.POP;
    }
}
