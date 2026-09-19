package com.maisonneuve.music_playlist_manager.model;

import java.time.LocalDate;

public class Song {
    private final String songId;
    private final String title;
    private final String artist;
    private final String album;
    private final int releaseDate;
    private final Genre genre;
    private final int duration;
    private int listenCount;

    private final LocalDate createdAt;

    public Song(
            String songId,
            String title,
            String artist,
            String album,
            int releaseDate,
            Genre genre,
            int duration,
            int listenCount
    ) {
        this.songId = songId;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.duration = duration;
        this.listenCount = listenCount;
        this.createdAt = LocalDate.now();
    }

    public String getSongId() {
        return songId;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public Genre getGenre() {
        return genre;
    }

    public int getDuration() {
        return duration;
    }

    public int getListenCount() {
        return listenCount;
    }

    public void incrementListenCount() {
        listenCount++;
    }
}
