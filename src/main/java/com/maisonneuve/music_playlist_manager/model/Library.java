package com.maisonneuve.music_playlist_manager.model;

import java.util.ArrayList;

public class Library {
    private ArrayList<Song> songs;
    private ArrayList<Playlist> playlists;

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
        this.songs = new ArrayList<Song>();
        this.playlists = new ArrayList<Playlist>();
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    /**
     * Adds a playlist to the Library
     * Doesnt allow duplicates
     * @param playlist
     */
    public void addPlaylist(Playlist playlist) {
        if (this.playlists.contains(playlist)) return;
        this.playlists.add(playlist);
    }

    /**
     * Removes a playlist from the Library
     * @param playlist
     */
    public void removePlaylist(Playlist playlist) {
        this.playlists.remove(playlist);
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    /**
     * Adds a song to the Library
     * Doesnt allow duplicates
     * @param song
     */
    public void addSong(Song song) {
        if (this.songs.contains(song)) return;
        this.songs.add(song);
    }

    /**
     * remo
     * @param song
     */
    public void removeSong(Song song) {
        this.songs.remove(song);
    }
}
