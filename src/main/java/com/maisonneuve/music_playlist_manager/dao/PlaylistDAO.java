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
     * Creates a playlist into the db
     * @param p
     * @throws SQLException
     */
    public void createPlaylist(Playlist p) throws SQLException {
        String sql =
                "INSERT INTO playlist "
                + "(playlist_id, name) "
                + "VALUES (?, ?)";
        try (Connection co = Connexion.open();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, p.getPlaylistId());
            ps.setString(2, p.getName());
            ps.executeUpdate();
        }
    }

    /**
     * Returns a playlist using its id
     * @param playlistId
     * @return
     * @throws SQLException
     */
    public Playlist getPlaylistById(String playlistId) throws SQLException {
        String sql =
                "SELECT * FROM playlist "
                + "WHERE playlist_id=?";
        try (Connection co = Connexion.open();
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
     * Returns a playlist using its name
     * Doesnt support cas insentitive and progressive search yet
     * @param name
     * @return
     * @throws SQLException
     */
    public Playlist getPlaylistByName(String name) throws SQLException {
        String sql =
                "SELECT * FROM playlist "
                        + "WHERE name=?";
        try (Connection co = Connexion.open();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapper(rs);
                }
                return null;
            }
        }
    }

    /**
     * Updates the playlist
     * Will be called when songs are added to the playlist even if nothing changes in its values
     * @param p
     * @throws SQLException
     */
    public void updatePlaylist(Playlist p) throws SQLException {
        String sql =
                "UPDATE playlist "
                + "SET name=?, updated_at=CURRENT_TIMESTAMP "
                + "WHERE playlist_id=?";
        try (Connection co = Connexion.open();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getPlaylistId());
            ps.executeUpdate();
        }
    }

    public void deletePlaylist(String playlistId) throws SQLException {
        String sql =
                "DELETE FROM playlist WHERE playlist_id=?";
        try (Connection co = Connexion.open();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, playlistId);
            ps.executeUpdate();
        }
    }

    /**
     * Transform a query result of SELECT * FROM playlist into Playlist object
     * @param rs
     * @return
     * @throws SQLException
     */
    private Playlist mapper(ResultSet rs) throws SQLException {
        Playlist p = new Playlist();
        p.setPlaylistId(rs.getString("playlist_id"));
        p.setName(rs.getString("name"));
        p.setCreatedAt(rs.getDate("created_at").toLocalDate());
        p.setUpdatedAt(rs.getDate("updated_at").toLocalDate());
        return p;
    }
}
