package com.maisonneuve.music_playlist_manager.model;

import java.time.LocalDate;

public class PlaylistSong {
    private final String playlistId;
    private final String songId;
    private int position;
    private final LocalDate createdAt;
    private LocalDate updatedAt;

    public PlaylistSong(
            String playlistId,
            String songId,
            int position
    ) {
        this.playlistId = playlistId;
        this.songId = songId;
        this.position = position;
        this.createdAt = LocalDate.now();
        this.updatedAt = createdAt;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public String getSongId() {
        return songId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
}
