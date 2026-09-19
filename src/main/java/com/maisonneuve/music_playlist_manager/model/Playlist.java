package com.maisonneuve.music_playlist_manager.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class Playlist {
    private final String playlistId;
    private final String libraryId;
    private String name;
    private ArrayList<String> songIds;
    private int runtime;

    private final LocalDate createdAt;
    private LocalDate updatedAt;

    public Playlist(
            String libraryId,
            String name
    ) {
        this.playlistId = UUID.randomUUID().toString();
        this.libraryId = libraryId;

        this.createdAt = LocalDate.now();
        this.updatedAt = createdAt;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public String getLibraryId() {
        return libraryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getSongIds() {
        return songIds;
    }

    /**
     * Adds given songs to the playlist
     * Allows multiple songs to be added or just one depends on the list size
     * @param songIds
     */
    public void addSong(ArrayList<String> songIds) {
        // if playlist already contains the song do nothing
        for (String song : songIds) {
            if (this.songIds.contains(song)) return;
        }

        this.songIds.addAll(songIds);
    }

    /**
     * Removes given songs from the playlist
     * Allows multiple songs to be removed or just one depends on the list size
     * @param songIds
     */
    public void removeSong(ArrayList<String> songIds) {
        this.songIds.removeAll(songIds);
    }

    /**
     * Changes the order of the song in the playlist
     * Only allows one song at a time to be reordered
     * to place song at the first postion 0 is the proper input
     * Should be handled in UI to give proper value here
     * @param songId
     * @param position
     */
    public void reorderSong(String songId, int position) {
        int currentPosition = this.songIds.indexOf(songId);

        if (currentPosition == -1) {
            return; // song doesnt exist
        }

        this.songIds.remove(currentPosition);
        this.songIds.add(position, songId);
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
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

    /**
     * removes all songs from the playlist
     */
    public void clear() {
        this.songIds.clear();
    }

    @Override
    public String toString() {
        return playlistId + "-" + libraryId + " (" + name + ", nbr of songs: " + songIds.size() + ", runtime: " + runtime + ")";
    }
}
