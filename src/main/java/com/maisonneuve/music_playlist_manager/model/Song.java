package com.maisonneuve.music_playlist_manager.model;

import java.time.LocalDate;

public class Song {
    private  String songId;
    private  String title;
    private  String artist;
    private  String album;
    private  int releaseYear;
    private  Genre genre;
    private  int durationSeconds;
    private int listenCount;

    private LocalDate createdAt;
    private LocalDate updatedAt;

    public Song(
    ) {
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

    public int getReleaseYear() {
        return releaseYear;
    }

    public Genre getGenre() {
        return genre;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public int getListenCount() {
        return listenCount;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setDurationSeconds(int durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public void setListenCount(int listenCount) {
        this.listenCount = listenCount;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return songId + "-" + title;
    }
}
