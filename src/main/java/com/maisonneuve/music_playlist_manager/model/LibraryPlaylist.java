package com.maisonneuve.music_playlist_manager.model;

import java.time.LocalDate;

public class LibraryPlaylist {
    private String libraryId;
    private String playlistId;

    private LocalDate createdAt;
    private LocalDate updatedAt;

    public LibraryPlaylist() {
        this.createdAt = LocalDate.now();
        this.updatedAt = createdAt;
    }

    public String getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return libraryId + "-" + playlistId;
    }
}
