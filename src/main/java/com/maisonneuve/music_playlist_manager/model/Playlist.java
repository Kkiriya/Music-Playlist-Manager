package com.maisonneuve.music_playlist_manager.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class Playlist {
    private  String playlistId;
    private String name;
    private ArrayList<String> songIds;
    private int runtime;

    private  LocalDate createdAt;
    private LocalDate updatedAt;

    public Playlist() {
        this.playlistId = UUID.randomUUID().toString();
        this.songIds = new ArrayList<>();

        this.createdAt = LocalDate.now();
        this.updatedAt = createdAt;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getSongIds() {
        return songIds;
    }

    /**
     * Returns the position of a given song in the list
     * @param songId
     * @return -1 if not found
     */
    public int getSongPositition(String songId) {
        for (int i = 0; i < songIds.size(); i++) {
            if (songIds.get(i).equals(songId)) return i;
        }
        return -1;
    }

    public int getRuntime() {
        return runtime;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSongIds(ArrayList<String> songIds) {
        this.songIds = songIds == null ? new ArrayList<>() : songIds;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return name == null ? playlistId : name;
    }
}
