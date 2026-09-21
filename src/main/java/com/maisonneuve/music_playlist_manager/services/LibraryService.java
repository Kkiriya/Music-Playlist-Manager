package com.maisonneuve.music_playlist_manager.services;

import com.maisonneuve.music_playlist_manager.dao.*;
import com.maisonneuve.music_playlist_manager.model.Library;
import com.maisonneuve.music_playlist_manager.model.LibrarySongs;
import com.maisonneuve.music_playlist_manager.model.Playlist;
import com.maisonneuve.music_playlist_manager.model.Song;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibraryService {
    private final LibraryDAO libraryDAO;
    private final SongDAO songDAO;
    private final PlaylistDAO playlistDAO;
    private final LibrarySongsDAO librarySongsDAO;
    private final LibraryPlaylistDAO libraryPlaylistDAO;

    private final SongService songService;
    private final PlaylistService playlistService;

    public LibraryService() {
        this.libraryDAO = new LibraryDAO();
        this.songDAO = new SongDAO();
        this.playlistDAO = new PlaylistDAO();
        this.librarySongsDAO = new LibrarySongsDAO();
        this.libraryPlaylistDAO = new LibraryPlaylistDAO();

        this.songService = new SongService();
        this.playlistService = new PlaylistService();
    }

    /**
     * Creates a new library in the db
     * @param l
     * @throws SQLException
     */
    public void createLibrary(Library l) throws SQLException {
        if (l == null) throw new IllegalArgumentException("Library cannot be empty");

        // verifies if library already exists
        if (libraryDAO.getLibrary(l.getLibraryId()) != null) throw new IllegalArgumentException("Library already " +
                "exists: " + l.getLibraryId());

        // creates the library in the db
        libraryDAO.createLibrary(new Library());
    }

    /**
     * Returns the library corresponding to the library id
     * @param libraryId
     * @return
     * @throws SQLException
     */
    public Library getLibraryById(String libraryId) throws SQLException {
        if (isEmptyStr(libraryId)) throw new IllegalArgumentException("library id cannot be empty");

        // verifies the library exists
        if (libraryDAO.getLibrary(libraryId) == null) throw new IllegalArgumentException("Library does not exist: " + libraryId);

        // gets the library info from db
        Library l = libraryDAO.getLibrary(libraryId);

        // populates the library with its corresponding songIds
        l.setSongIds(librarySongsDAO.getLibrarySongsIds(l.getLibraryId()));
        // populates the library with its corresponding playlistIds
        l.setPlaylistsIds(libraryPlaylistDAO.getLibraryPlaylistIds(l.getLibraryId()));

        return l;
    }

    /**
     * Only used to reflect updates in the library through the updated_at value
     * @param l
     * @throws SQLException
     */
    public void updateLibrary(Library l)throws SQLException {
        if (l == null) throw new IllegalArgumentException("Library cannot be empty");

        // verifies the library exists
        if (libraryDAO.getLibrary(l.getLibraryId()) == null) throw new IllegalArgumentException("Library does not " +
                "exist: " + l.getLibraryId());

        libraryDAO.updateLibrary(l);
    }

    /**
     * Deletes the given library
     * @param libraryId
     * @throws SQLException
     */
    public void deleteLibrary(String libraryId) throws SQLException {
        if (isEmptyStr(libraryId)) throw new IllegalArgumentException("library id cannot be empty");

        // verifies library exist
        if (libraryDAO.getLibrary(libraryId) == null) throw new IllegalArgumentException("Library does not exist: " + libraryId);

        libraryDAO.deleteLibrary(libraryId);
    }

    /**
     * Adds a song to the library and create all neccessary relations in db
     * @param song
     * @param libraryId
     * @throws SQLException
     */
    public void addSongToLibrary(Song song, String libraryId) throws SQLException {
        if (song == null) throw new IllegalArgumentException("song cannot be empty");
        if (isEmptyStr(libraryId)) throw new IllegalArgumentException("library id cannot be empty");

        // verify that the song exists
        if (songDAO.getSongById(song.getSongId()) == null) throw new IllegalArgumentException("song does not exist " +
                song.getSongId());

        // verify that the library exist
        if (libraryDAO.getLibrary(libraryId) == null) throw new IllegalArgumentException("library doesn not exist: " + libraryId);

        // gets the actual library object
        Library library = getLibraryById(libraryId);

        // add songs to library
        List<String> songIds = library.getSongIds();
        songIds.add(song.getSongId());
        library.setSongIds(songIds);

        // creates the libary_song object in db
        librarySongsDAO.createLibrarySongs(song.getSongId(), library.getLibraryId());

        // updates the library
        libraryDAO.updateLibrary(library);
    }

    /**
     * Deletes a song From the library and removes all neccessary relations in db
     * @param song
     * @param libraryId
     * @throws SQLException
     */
    public void removeSongFromLibrary(Song song, String libraryId) throws SQLException {
        if (song == null) throw new IllegalArgumentException("song cannot be empty");
        if (isEmptyStr(libraryId)) throw new IllegalArgumentException("library id cannot be empty");

        // verify that the song exists
        if (songDAO.getSongById(song.getSongId()) == null) throw new IllegalArgumentException("song does not exist " +
                song.getSongId());

        // verify that the library exist
        if (libraryDAO.getLibrary(libraryId) == null) throw new IllegalArgumentException("library doesn not exist: " + libraryId);

        // gets the actual library object
        Library library = getLibraryById(libraryId);

        // remove song from the library
        List<String> songIds = library.getSongIds();
        songIds.remove(song.getSongId());
        library.setSongIds(songIds);

        // deletes the library_song object in db
        librarySongsDAO.deleteLibrarySongs(song.getSongId(), libraryId);

        // updates the library
        libraryDAO.updateLibrary(library);
    }

    /**
     * Returns a song if it is in the library
     * @param songId
     * @param libraryId
     * @return
     * @throws SQLException
     */
    public Song getSongById(String songId, String libraryId) throws SQLException {
        if (isEmptyStr(songId)) throw new IllegalArgumentException("song id cannot be empty");
        if (isEmptyStr(libraryId)) throw new IllegalArgumentException("library id cannot be empty");

        // verifies song actually exists
        if (songDAO.getSongById(songId) == null) throw new SQLException("song does not exist:" + songId);

        // verifies libraryId actually exists
        if (libraryDAO.getLibrary(libraryId) == null) throw new SQLException("Library does not exist: " + libraryId);

        // fetches the library_song item
        LibrarySongs libSong = librarySongsDAO.getLibrarySongs(songId, libraryId);

        // checks if song exists in the library
        if (libSong == null) throw new IllegalArgumentException("Song does not exist in the library");

        // returns the actuall song object
        return songService.getSongById(libSong.getSongId());
    }

    /**
     * Returns a song if it is in the library
     * @param title
     * @param libraryId
     * @return
     * @throws SQLException
     */
    public Song getSongByTite(String title, String libraryId) throws SQLException {
        if (isEmptyStr(title)) throw new IllegalArgumentException("song id cannot be empty");
        if (isEmptyStr(libraryId)) throw new IllegalArgumentException("library id cannot be empty");

        // verifies song actually exists
        if (songDAO.getSongByTitle(title) == null) throw new SQLException("song does not exist:" + title);

        // verifies libraryId actually exists
        if (libraryDAO.getLibrary(libraryId) == null) throw new SQLException("Library does not exist: " + libraryId);

        // fetches the song item to get its id
        Song song = songService.getSongByTitle(title);

        // fetches the library_song item
        LibrarySongs libSong = librarySongsDAO.getLibrarySongs(song.getSongId(), libraryId);

        // checks if song exists in the library
        if (libSong == null) throw new IllegalArgumentException("Song does not exist in the library");

        // returns the actual song object
        return song;
    }

    /**
     * Returns all songs in the given library
     * @param libraryId
     * @return
     * @throws SQLException
     */
    public ArrayList<Song> getAllSongs(String libraryId) throws SQLException {
        if (isEmptyStr(libraryId)) throw new IllegalArgumentException("library id cannot be empty");

        // verifies the library actually exists
        if (libraryDAO.getLibrary(libraryId) == null) throw new IllegalArgumentException("Library doesn not exist: " + libraryId);

        // fetches all the songIds of the song in the library
        ArrayList<String> songIds = librarySongsDAO.getLibrarySongsIds(libraryId);
        // populates a list with the song Obejcts that correspond to the ones in the lib
        ArrayList<Song> songs = new ArrayList<>();
        for (String id : songIds) {
            songs.add(songDAO.getSongById(id));
        }

        return songs;
    }

    /**
     * Returns a specifief playlist if it is in the library
     * @param playlistId
     * @param libraryId
     * @return
     * @throws SQLException
     */
    public Playlist getPlaylistById(String playlistId, String libraryId) throws SQLException {
        if (isEmptyStr(playlistId)) throw new IllegalArgumentException("playlist id cannot be empty");
        if(isEmptyStr(libraryId)) throw new IllegalArgumentException("library id cannot be empty");

        // verifies that the playlist actually exists
        if (playlistDAO.getPlaylistById(playlistId) == null) throw new IllegalArgumentException("Playlist does not " +
                "exist: " + playlistId);

        // verifies that the library actaully exist
        if (libraryDAO.getLibrary(libraryId) == null) throw new IllegalArgumentException("Library does not exists: " + libraryId);

        return playlistService.getPlaylistById(playlistId);
    }

    /**
     * Returns a specified playlist if it is in the library
     * @param name
     * @param libraryId
     * @return
     * @throws SQLException
     */
    public Playlist getPlaylistByName(String name, String libraryId) throws SQLException {
        if (isEmptyStr(name)) throw new IllegalArgumentException("playlist name cannot be empty");
        if(isEmptyStr(libraryId)) throw new IllegalArgumentException("library id cannot be empty");

        // verifies that the playlist actually exists
        if (playlistDAO.getPlaylistById(name) == null) throw new IllegalArgumentException("Playlist does not " +
                "exist: " + name);

        // verifies that the library actaully exist
        if (libraryDAO.getLibrary(libraryId) == null) throw new IllegalArgumentException("Library does not exists: " + libraryId);

        return playlistService.getPlaylistByName(name);
    }

    /**
     * Returns all playlists in the given library
     * @param libraryId
     * @return
     * @throws SQLException
     */
    public ArrayList<Playlist> getAllPlaylists(String libraryId) throws SQLException {
        if (isEmptyStr(libraryId)) throw new IllegalArgumentException("library id cannot be empty");

        // verifies that the library actually exsits
        if (libraryDAO.getLibrary(libraryId) == null) throw new IllegalArgumentException("Library does not exists: " + libraryId);

        // fetches all the playlistIds of the library
        ArrayList<String> playlistIds = libraryPlaylistDAO.getLibraryPlaylistIds(libraryId);
        // populates a list with the song Obejcts that correspond to the ones in the lib
        ArrayList<Playlist> playlists = new ArrayList<>();
        for (String id : playlistIds) {
            playlists.add(playlistDAO.getPlaylistById(id));
        }

        return playlists;
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
