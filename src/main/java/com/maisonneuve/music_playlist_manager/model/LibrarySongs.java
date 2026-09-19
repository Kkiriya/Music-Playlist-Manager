package com.maisonneuve.music_playlist_manager.model;

import java.time.LocalDate;

public class LibrarySongs {
    private final String libraryId;
    private final String songId;

    private final LocalDate createdAt;
    private final LocalDate updatedAt;

    public LibrarySongs(
            String libraryId,
            String songId
    ) {
        this.libraryId = libraryId;
        this.songId = songId;
        this.createdAt = LocalDate.now();
        this.updatedAt = createdAt;
    }

    public String getLibraryId() {
        return libraryId;
    }

    public String getSongId() {
        return songId;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }
}
