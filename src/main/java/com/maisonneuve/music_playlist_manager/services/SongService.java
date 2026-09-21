package com.maisonneuve.music_playlist_manager.services;

import com.maisonneuve.music_playlist_manager.model.Song;
import com.maisonneuve.music_playlist_manager.dao.SongDAO;

import java.sql.SQLException;
import java.util.List;

/**
 * Handles all operation that directly happen on songs
 */
public class SongService {
    private final SongDAO songDAO;

    public SongService() {
        this.songDAO = new SongDAO();
    }

    /**
     * Fetches a song by its id
     * @param songId
     * @return
     * @throws SQLException
     */
    public Song getSongById(String songId) throws SQLException {
        if (isEmptyStr(songId)) throw new IllegalArgumentException("Song id cannot be empty");
        return songDAO.getSongById(songId);
    }

    /**
     * Fetches a song by its title
     * @param title
     * @return
     * @throws SQLException
     */
    public Song getSongByTitle(String title) throws SQLException {
        if (isEmptyStr(title)) throw new IllegalArgumentException("Song title cannot be empty");
        return songDAO.getSongByTitle(title);
    }

    /**
     * Fetches a list of all song in the catalogue
     * @return
     * @throws SQLException
     */
    public List<Song> getAllSongs() throws SQLException {
        return songDAO.getAllSongs();
    }

    /**
     * Raises the listenCount of a song
     * @param songId
     * @throws SQLException
     */
    public void raiseListenCount(String songId) throws SQLException {
        if (isEmptyStr(songId)) throw new IllegalArgumentException("Song id cannot be empty");

        // making sure the song exist first
        Song s = songDAO.getSongById(songId);

        if (s == null) throw new IllegalArgumentException("Song not found: " + songId);

        songDAO.raiseListenCount(songId);
    }

    /**
     * Small helper function to validate strings args
     * @param str
     * @return true if empty, false if not
     */
    private boolean isEmptyStr(String str) {
        return (str == null || str.isBlank());
    }
}
