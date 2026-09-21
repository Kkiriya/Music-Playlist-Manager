package com.maisonneuve.music_playlist_manager.services;

import com.maisonneuve.music_playlist_manager.dao.*;
import com.maisonneuve.music_playlist_manager.model.Library;
import com.maisonneuve.music_playlist_manager.model.LibraryPlaylist;
import com.maisonneuve.music_playlist_manager.model.Playlist;
import com.maisonneuve.music_playlist_manager.model.Song;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistService {
    private final LibraryDAO libraryDAO;
    private final PlaylistDAO playlistDAO;
    private final SongDAO songDAO;
    private final PlaylistSongDAO playlistSongDAO;
    private final LibraryPlaylistDAO libraryPlaylistDAO;

    public PlaylistService() {
        this.libraryDAO = new LibraryDAO();
        this.playlistDAO = new PlaylistDAO();
        this.songDAO = new SongDAO();
        this.playlistSongDAO = new PlaylistSongDAO();
        this.libraryPlaylistDAO = new LibraryPlaylistDAO();
    }

    /**
     * Creates a playlist without requiring a library link.
     */
    public void createPlaylist(Playlist p) throws SQLException {
        validatePlaylistForSave(p);

        if (playlistDAO.getPlaylistById(p.getPlaylistId()) != null) {
            throw new IllegalArgumentException("Playlist already exists: " + p.getPlaylistId());
        }

        if (playlistDAO.getPlaylistByName(p.getName()) != null) {
            throw new IllegalArgumentException("Playlist name already exists: " + p.getName());
        }

        playlistDAO.createPlaylist(p);
    }

    /**
     * Creates a playlist and all its neccessary add-ons in the db
     * From the provided playlist item and the library in which it is created
     * Library should always be created empty (without songs)
     * @param p
     * @param libraryId
     */
    public void createPlaylist(Playlist p, String libraryId) throws SQLException {
        if (isEmptyStr(libraryId)) throw new IllegalArgumentException("library Id cannot be empty");
        if (p == null) throw new IllegalArgumentException("Playlist object must be provided");

        if (p.getPlaylistId() == null) throw new IllegalArgumentException("Playlist must be initialised");
        if (p.getName() == null) throw new IllegalArgumentException("Playlist must have a name");

        // checks if playlist already exists
        if (playlistDAO.getPlaylistById(p.getPlaylistId()) != null) throw new IllegalArgumentException("Playlist " +
                "already exists");

        // checks if library exsits
        if (libraryDAO.getLibrary(libraryId) == null) throw new IllegalArgumentException("Library does not exist: " + libraryId);

        // creates the playlist in the db
        playlistDAO.createPlaylist(p);

        // links the playlist created to its assigned library in db
        libraryPlaylistDAO.createLibraryPlaylist(p.getPlaylistId(), libraryId);
    }

    /**
     * Fetches a playlist from the db
     * @param playlistId
     * @return
     * @throws SQLException
     */
    public Playlist getPlaylistById(String playlistId) throws SQLException {
        if (isEmptyStr(playlistId)) throw new IllegalArgumentException("playlist id cannot be empty");

        // Gets the playlist info from the db
        Playlist playlist = playlistDAO.getPlaylistById(playlistId);

        // checks if playlist exists
        if (playlist == null) throw new IllegalArgumentException("playlist does not exist: " + playlistId);

        // populates the playlist with its proper songIds
        playlist.setSongIds(playlistSongDAO.getOrderedPlaylist(playlist.getPlaylistId()));

        playlist.setRuntime(0);
        // calculates the runtime for the playlist
        for (String songId: playlist.getSongIds()) {
            // adds current song runtime to the current playlist runtime
            playlist.setRuntime(
                    playlist.getRuntime() +
                            songDAO.getSongById(songId).getDurationSeconds()
            );
        }

        return playlist;
    }

    /**
     * Fetches a playlist from the db
     * @param name
     * @return
     * @throws SQLException
     */
    public Playlist getPlaylistByName(String name) throws SQLException {
        if (isEmptyStr(name)) throw new IllegalArgumentException("playlist name cannot be empty");

        // Gets the playlist info from the db
        Playlist playlist = playlistDAO.getPlaylistByName(name);

        // checks if playlist exists
        if (playlist == null) throw new IllegalArgumentException("playlist does not exist: " + name);

        // populates the playlist with its proper songIds
        playlist.setSongIds(playlistSongDAO.getOrderedPlaylist(playlist.getPlaylistId()));

        playlist.setRuntime(0);
        // calculates the runtime for the playlist
        for (String songId: playlist.getSongIds()) {
            // adds current song runtime to the current playlist runtime
            playlist.setRuntime(
                    playlist.getRuntime() +
                            songDAO.getSongById(songId).getDurationSeconds()
            );
        }

        return playlist;
    }

    /**
     * Returns a list of all playlist in the given library
     * @param libraryId
     * @return
     * @throws SQLException
     */
    public List<Playlist> getAllPlaylists(String libraryId) throws SQLException {
        if(isEmptyStr(libraryId)) throw new IllegalArgumentException("Library id cannot be empty");

        // checks if library exist
        if (libraryDAO.getLibrary(libraryId) == null) throw new IllegalArgumentException("Library does not exist: " + libraryId);

        // List to contain the fetched Playlist
        List<Playlist> playlists = new ArrayList<>();

        // fetches the list of all playlistId contained in the given library
        List<String> playlistIds = libraryPlaylistDAO.getAllLibraryPlaylist(libraryId);

        // for all id get the playlist object and add it to the list
        for (String id : playlistIds) {
            playlists.add(getPlaylistById(id));
        }

        return playlists;
    }

    /**
     * Returns every playlist without filtering by library.
     */
    public List<Playlist> getAllPlaylists() throws SQLException {
        List<Playlist> playlists = playlistDAO.getAllPlaylists();

        for (Playlist playlist : playlists) {
            playlist.setSongIds(playlistSongDAO.getOrderedPlaylist(playlist.getPlaylistId()));
            playlist.setRuntime(0);
            for (String songId : playlist.getSongIds()) {
                Song song = songDAO.getSongById(songId);
                if (song != null) {
                    playlist.setRuntime(playlist.getRuntime() + song.getDurationSeconds());
                }
            }
        }

        return playlists;
    }

    /**
     * Updates specified playlist
     * @param p
     * @throws SQLException
     */
    public void updatePlaylist(Playlist p)throws SQLException {
        validatePlaylistForSave(p);

        // checks if playlist exists
        if (playlistDAO.getPlaylistById(p.getPlaylistId()) == null) throw new IllegalArgumentException("Playlist does" +
                " not exist: " + p.getPlaylistId());

        playlistDAO.updatePlaylist(p);
    }

    /**
     * Delets a playlist
     * Thanks to ON DELETE CASCADE we do not need to manally delete the
     * library link as well
     * @param playlistId
     */
    public void deletePlaylist(String playlistId) throws SQLException {
        if (isEmptyStr(playlistId)) throw new IllegalArgumentException("Playlist id cannot be empty");

        // checks if playlist exists
        if (playlistDAO.getPlaylistById(playlistId) == null) throw new IllegalArgumentException("Playlist does not " +
                "exist: " + playlistId);

        playlistDAO.deletePlaylist(playlistId);
    }

    /**
     * Adds a song to the playlist by creating a playlist_song line in the db
     * Also updates the value of updated_at in playlist table
     * @param songId
     * @param playlistId
     * @throws SQLException
     */
    public void addSongToPlaylist(String songId, String playlistId) throws SQLException {
        if (isEmptyStr(songId)) throw new IllegalArgumentException("song id cannot be empty");
        if (isEmptyStr(playlistId)) throw new IllegalArgumentException("playlist id cannot be empty");

        // verify song exist
        if (songDAO.getSongById(songId) == null) throw new IllegalArgumentException("Song not found: " + songId);

        // verify playlist exist
        if (playlistDAO.getPlaylistById(playlistId) == null) throw new IllegalArgumentException("Playlist not found: " + playlistId);

        // gets the actualy playlist
        Playlist playlist = getPlaylistById(playlistId);
        if (playlist.getSongIds().contains(songId)) {
            throw new IllegalArgumentException("Song already exists in playlist: " + songId);
        }

        ArrayList<String> songIds = playlist.getSongIds();
        songIds.add(songId);
        playlist.setSongIds(songIds);

        // creates the playlist_song object
        playlistSongDAO.createPlaylistSong(songId, playlist.getPlaylistId(), playlist.getSongPositition(songId));

        // updates the playlist updated_at
        playlistDAO.updatePlaylist(playlist);
    }

    /**
     * Deletes a song from a playlist by deleting corresponding the playlist_song line
     * Also updates the updated_at line in the playlist table
     * @param songId
     * @param playlistId
     * @throws SQLException
     */
    public void removeSongFromPlaylist(String songId, String playlistId) throws SQLException {
        if (isEmptyStr(songId)) throw new IllegalArgumentException("song id cannot be empty");
        if (isEmptyStr(playlistId)) throw new IllegalArgumentException("playlist id cannot be empty");

        // verify song exist
        if (songDAO.getSongById(songId) == null) throw new IllegalArgumentException("Song not found: " + songId);

        // verify playlist exist
        Playlist p = getPlaylistById(playlistId);
        if (p == null) throw new IllegalArgumentException("Playlist not found: " + playlistId);
        if (!p.getSongIds().contains(songId)) {
            throw new IllegalArgumentException("Song does not exist in playlist: " + songId);
        }

        // deletes the playlist_song object
        playlistSongDAO.deletePlaylistSong(songId, playlistId);

        // updates the playlist updated_at
        playlistDAO.updatePlaylist(p);
    }

    public void reorderSongInPlaylist(String songId, String playlistId, int position) throws SQLException {
        if (isEmptyStr(songId)) throw new IllegalArgumentException("song id cannot be empty");
        if (isEmptyStr(playlistId)) throw new IllegalArgumentException("playlist id cannot be empty");
        if (position < 0) throw new IllegalArgumentException("position cannot be under 0");

        // verify song exist
        if (songDAO.getSongById(songId) == null) throw new IllegalArgumentException("Song not found: " + songId);

        // verify playlist exist
        Playlist p = getPlaylistById(playlistId);
        if (p == null) throw new IllegalArgumentException("Playlist not found: " + playlistId);

        // verify position is within the size of the list
        if (position > p.getSongIds().size()) throw new IllegalArgumentException("position cannot be bigger then the " +
                "playlist size itself");

        // reorders the playlist
        ArrayList<String> songs = p.getSongIds(); // ordered list of songIds
        int oldPos = songs.indexOf(songId); // keeps the old position in memory
        // Does the switch
        if (oldPos != -1) {
            songs.remove(oldPos);
            songs.add(position, songId);
        }

        // updates the playlist with the new order
        for (int i = 0; i < songs.size(); i++) {
            playlistSongDAO.updatePlaylistSong(songs.get(i), playlistId, i);
        }
        playlistDAO.updatePlaylist(p);
    }

    /**
     * Returns the songs contained in a playlist, in playlist order.
     */
    public List<Song> getPlaylistSongs(String playlistId) throws SQLException {
        Playlist playlist = getPlaylistById(playlistId);
        List<Song> songs = new ArrayList<>();

        for (String songId : playlist.getSongIds()) {
            Song song = songDAO.getSongById(songId);
            if (song != null) {
                songs.add(song);
            }
        }

        return songs;
    }

    private void validatePlaylistForSave(Playlist p) {
        if (p == null) throw new IllegalArgumentException("Playlist object must be provided");
        if (isEmptyStr(p.getPlaylistId())) throw new IllegalArgumentException("Playlist must be initialised");
        if (isEmptyStr(p.getName())) throw new IllegalArgumentException("Playlist must have a name");
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
