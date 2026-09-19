package com.maisonneuve.music_playlist_manager.dao;

import com.maisonneuve.music_playlist_manager.model.PlaylistSong;
import com.maisonneuve.music_playlist_manager.util.Connexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PlaylistSongDAO {

    /**
     * creates a new entry for create playlist song
     * @param pls
     * @throws SQLException
     */
    public void createPlaylistSong(PlaylistSong pls) throws SQLException {
        String sql =
                "INSERT INTO playlist_song "
                + "(playlist_id, song_id, position) "
                + "VALUES (?, ?, ?)";

        try (
                Connection co = Connexion.open();
                PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, pls.getPlaylistId());
            ps.setString(2, pls.getSongId());
            ps.setInt(3, pls.getPosition());

            ps.executeUpdate();
        }
    }

    /**
     * Effectively updates the position of a song in a playlist
     * @param pls
     * @throws SQLException
     */
    public void updatePlaylistSong(PlaylistSong pls) throws SQLException {
        String sql =
                "UPDATE playlist_song "
                + "SET position=? "
                + "WHERE playlist_id=? AND song_id=?";

        try (
                Connection co = Connexion.open();
                PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setInt(1, pls.getPosition());
            ps.setString(2, pls.getPlaylistId());
            ps.setString(3, pls.getSongId());

            ps.executeUpdate();
        }
    }

    /**
     * Effectively deletes a song from a playlist
     * @param playlist_id
     * @param song_id
     * @throws SQLException
     */
    public void deletePlaylistSong(String playlist_id, String song_id) throws SQLException {
        String sql = "DELETE FROM playlist_song WHERE playlist_id=? AND song_id=?";

        try (
                Connection co = Connexion.open();
                PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, playlist_id);
            ps.setString(2, song_id);

            ps.executeUpdate();
        }
    }

    public PlaylistSong getPlaylistSong(String playlist_id, String song_id) throws SQLException {
            String sql = "SELECT * FROM playlist_song "
                    + "WHERE playlist_id=? AND song_id=?";

        try (
                Connection co = Connexion.open();
                PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, playlist_id);
            ps.setString(2, song_id);

            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapper(rs);
                }
                return null;
            }
        }
    }

    private PlaylistSong mapper(ResultSet rs) throws SQLException {
            return new PlaylistSong(
                    rs.getString("playlist_id"),
                    rs.getString("song_id"),
                    rs.getInt("position")
            );
    }
}
