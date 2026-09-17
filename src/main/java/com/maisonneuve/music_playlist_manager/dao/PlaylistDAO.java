package com.maisonneuve.music_playlist_manager.dao;

import com.maisonneuve.music_playlist_manager.model.Playlist;
import com.maisonneuve.music_playlist_manager.util.Connexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {

    /**
     * Adds a new playlist to the db from the playlist object given
     * @param p
     * @throws SQLException
     */
    public void createPlaylist(Playlist p) throws SQLException {
        String sql = "INSERT INTO playlist "
                + "(playlist_id, name) "
                + "VALUES (?, ?)";

        try (
                Connection co = Connexion.open();
                PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, p.getPlaylistId());
            ps.setString(2, p.getName());

            ps.executeUpdate();
        }
    }

    /**
     * updates a playlist with the new playlist item provided
     * @param p
     * @throws SQLException
     */
    public void updatePlaylist(Playlist p) throws SQLException {
        String sql = "UPDATE playlist "
                + "SET name=?, updated_at=CURRRENT_TIMESTAMP "
                + "WHERE playlist_id=?";
        try (
                Connection co = Connexion.open();
                PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, p.getName());

            ps.executeUpdate();
        }
    }

    /**
     * Deletes a playlist
     * @param playlistId
     * @throws SQLException
     */
    public void deletePlaylist(String playlistId) throws SQLException {
        String sql = "DELETE FROM playlist WHERE playlist_id=?";
        try (
                Connection co = Connexion.open();
                PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, playlistId);
            ps.executeUpdate();
        }
    }

    /**
     * returns the playlist that matches the given id
     * @param playlistId
     * @return
     * @throws SQLException
     */
    public Playlist getPlaylist(String playlistId) throws SQLException {
        String sql =
                "SELECT * FROM playlist "
                + "WHERE playlist_id = ?";
        try (
                Connection co = Connexion.open();
                PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, playlistId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapper(rs);
                }
                return null;
            }
        }
    }

    /**
     * Returns a list of all playlist available in the db as a list of playlist objects
     * @return
     * @throws SQLException
     */
    public List<Playlist> getAllPlaylists() throws SQLException {
        String sql = "SELECT * FROM playlist ";

        List<Playlist> playlists = new ArrayList<>();

        try (
                Connection co = Connexion.open();
                PreparedStatement ps = co.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                playlists.add(mapper(rs));
            }
        }
        return playlists;
    }

    /**
     * Transform a query result of SELECT * FROM playlist into Playlist object
     * @param rs
     * @return
     * @throws SQLException
     */
    private Playlist mapper(ResultSet rs) throws SQLException {
        return new Playlist(
                rs.getString("playlist_id"),
                rs.getString("name")
        );
    }
}
