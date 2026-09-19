package com.maisonneuve.music_playlist_manager.model;

import java.util.ArrayList;
import java.util.UUID;

public class Library {
    private final String libraryId;
    private ArrayList<String> songIds;
    private ArrayList<String> playlistIds;

//    /**
//     * If user already has some songs and playlist load them up otherwise
//     * create empty lists
//     * @param loadedSongs
//     * @param loadedPlaylist
//     */
//    public Library(ArrayList<Song> loadedSongs, ArrayList<Playlist> loadedPlaylist) {
//        if(!loadedSongs.isEmpty() && !loadedPlaylist.isEmpty()) {
//            this.songs = loadedSongs;
//            this.playlists = loadedPlaylist;
//        } else {
//            // tbd
//        }
//    }

    /**
     * Default constructor for Library
     * PS. Library as of now acts as the users personal library of songs
     * loaded into memory, and allows us to store any songs the user wishes to
     * into DB, The songs the user sees on screen are loaded through straight
     * from the CSV or whatever date source we eventually have
     */
    public Library(){
        this.libraryId = UUID.randomUUID().toString(); // for when users are eventually implemented
        this.songIds = new ArrayList<String>();
        this.playlistIds = new ArrayList<String>();
    }

    public Library(String libraryId) {
        this.libraryId = libraryId;
        this.songIds = new ArrayList<String>();
        this.playlistIds = new ArrayList<String>();
    }

    public String getLibraryId() {
        return libraryId;
    }

    public ArrayList<String> getSongIds() {
        return songIds;
    }

    public void setSongIds(ArrayList<String> songIds) {
        this.songIds = songIds;
    }

    public ArrayList<String> getPlaylistIds() {
        return playlistIds;
    }

    public void setPlaylistIds(ArrayList<String> playlistIds) {
        this.playlistIds = playlistIds;
    }

    @Override
    public String toString() {
        return libraryId + "(nbr of songs: " + songIds.size() + ", nbr of playlists: " + playlistIds.size() + ")";
    }
}
