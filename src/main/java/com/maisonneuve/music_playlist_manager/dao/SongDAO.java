package com.maisonneuve.music_playlist_manager.dao;

import com.maisonneuve.music_playlist_manager.model.Genre;
import com.maisonneuve.music_playlist_manager.model.Song;
import com.maisonneuve.music_playlist_manager.util.Connexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongDAO {

    /**
     * Adds a new song to the database from the song object given
     * @param s
     * @throws SQLException
     */
    public void createSong(Song s) throws SQLException {
        String sql =
                "INSERT INTO song "
                + "(song_id, title, artist, album, release_year, genre, duration_seconds) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection co = Connexion.open();
                PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, s.getSongId());
            ps.setString(2, s.getTitle());
            ps.setString(3, s.getArtist());
            ps.setString(4, s.getAlbum());
            ps.setInt(5,s.getReleaseDate());
            ps.setString(6, s.getGenre().toString());
            ps.setInt(7, s.getDuration());

            ps.executeUpdate();
        }
    }

    /**
     * Raises the listen count of a song by 1
     * @param song_id
     * @throws SQLException
     */
    public void raiseSongListenCount(String song_id) throws SQLException {
        String sql = "UPDATE song "
                + "SET listen_count=listen_count+1, update_at=CURRENT_TIMESTAMP "
                + "WHERE song_id=?";
        try (
                Connection co = Connexion.open();
                PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, song_id);

            ps.executeUpdate();
        }
    }

    /**
     * Deletes a song from the db
     * @param songId
     * @throws SQLException
     */
    public void deleteSong(String songId) throws SQLException {
        String sql = "DELETE FROM song WHERE song_id=?";
        try (
                Connection co = Connexion.open();
                PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, songId);
            ps.executeUpdate();
        }
    }

    /**
     * Returns a song from the db as Song object
     * @param songId
     * @return
     * @throws SQLException
     */
    public Song getSong(String songId) throws SQLException {
        String sql =
                "SELECT * FROM song "
                + "WHERE song_id = ?";

        try (
                Connection co = Connexion.open();
                PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, songId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapper(rs);
                }
                return null;
            }
        }
    }

    /**
     * Returns a list of all songs available in the db as a list of Song objects
     * @return
     * @throws SQLException
     */
    public List<Song> getAllSongs() throws SQLException {
        String sql = "SELECT * FROM song ";

        List<Song> songs = new ArrayList<>();

        try (
                Connection co = Connexion.open();
                PreparedStatement ps = co.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                songs.add(mapper(rs));
            }
        }
        return songs;
    }

    public void remove(Song s) throws SQLException {

    }

    /**
     * Transform a result query of SELECT * FROM SONG into appropriate Song object
     * @param rs
     * @return
     * @throws SQLException
     */
    private Song mapper(ResultSet rs) throws SQLException {
        return new Song(
                rs.getString("song_id"),
                rs.getString("title"),
                rs.getString("artist"),
                rs.getString("album"),
                rs.getInt("release_year"),
                Genre.valueOf(rs.getString("genre")), // converts the stored string back into an enum
                rs.getInt("duration_seconds"),
                rs.getInt("listen_count")
        );
    }

}
