package com.maisonneuve.music_playlist_manager.model;

import java.time.LocalDate;

public class LibraryPlaylist {
    private final String libraryId;
    private final String playlistId;

    private final LocalDate createdAt;
    private final LocalDate updatedAt;

    public LibraryPlaylist(
            String libraryId,
            String playlistId
    ) {
        this.libraryId = libraryId;
        this.playlistId = playlistId;
        this.createdAt = LocalDate.now();
        this.updatedAt = createdAt;
    }

    public String getLibraryId() {
        return libraryId;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }
}
