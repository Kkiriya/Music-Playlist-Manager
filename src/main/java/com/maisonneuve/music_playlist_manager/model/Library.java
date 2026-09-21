package com.maisonneuve.music_playlist_manager.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Library {
    private String libraryId;
    private List<String> songIds;
    private List<String> playlistsIds;

    private LocalDate createdAt;
    private LocalDate updatedAt;

    public Library() {
        this.libraryId = UUID.randomUUID().toString();
        this.songIds = new ArrayList<>();
        this.playlistsIds = new ArrayList<>();
        this.createdAt = LocalDate.now();
        this.updatedAt = createdAt;
    }

    public String getLibraryId() {
        return libraryId;
    }

    public List<String> getSongIds() {
        return songIds;
    }

    public List<String> getPlaylistsIds() {
        return playlistsIds;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
    }

    public void setSongIds(List<String> songIds) {
        this.songIds = songIds;
    }

    public void setPlaylistsIds(List<String> playlistsIds) {
        this.playlistsIds = playlistsIds;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return libraryId;
    }
}
