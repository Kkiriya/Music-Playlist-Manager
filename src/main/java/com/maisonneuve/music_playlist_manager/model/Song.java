package com.maisonneuve.music_playlist_manager.model;

import java.time.LocalDate;

public class Song {
    private final String songId;
    private String title;
    private String artist;
    private String album;
    private String releaseDate;
    private Genre genre;
    private int duration;
    private int listenCount;

    private final LocalDate createdAt;
    private LocalDate updatedAt;

    public Song(
            String songId,
            String title,
            String artist,
            String album,
            String releaseDate,
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
        this.updatedAt = createdAt;
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

    public String getReleaseDate() {
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
