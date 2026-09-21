package com.maisonneuve.music_playlist_manager.model;

import java.time.LocalDate;

public class LibrarySongs {
    private String libraryId;
    private String songId;

    private LocalDate createdAt;
    private LocalDate updatedAt;

    public LibrarySongs() {
        this.createdAt = LocalDate.now();
        this.updatedAt = createdAt;
    }

    public String getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
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
        return libraryId = "-" + songId;
    }
}
