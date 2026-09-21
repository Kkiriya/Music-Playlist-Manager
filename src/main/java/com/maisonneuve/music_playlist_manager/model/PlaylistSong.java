package com.maisonneuve.music_playlist_manager.model;

import com.maisonneuve.music_playlist_manager.dao.PlaylistSongDAO;

import java.time.LocalDate;

public class PlaylistSong {
    private String playlistId;
    private String songId;
    private int position;

    private LocalDate createdAt;
    private LocalDate updatedAt;

    public PlaylistSong() {
        this.createdAt = LocalDate.now();
        this.updatedAt = createdAt;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
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

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
}
