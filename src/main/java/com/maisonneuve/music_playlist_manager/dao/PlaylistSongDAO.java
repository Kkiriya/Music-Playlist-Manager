package com.maisonneuve.music_playlist_manager.dao;

import com.maisonneuve.music_playlist_manager.model.Library;
import com.maisonneuve.music_playlist_manager.model.Playlist;
import com.maisonneuve.music_playlist_manager.model.PlaylistSong;
import com.maisonneuve.music_playlist_manager.util.Connexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PlaylistSongDAO {

    /**
     * Creates a link between a song and a playlist in the db
     * @param songId
     * @param playlistId
     * @param songPosition
     * @throws SQLException
     */
    public void createPlaylistSong(String songId, String playlistId, int songPosition) throws SQLException {
        String sql =
                "INSERT INTO playlist_song "
                + "(playlist_id, song_id, position) "
                + "VALUES (?, ?, ?)";
        try (Connection co = Connexion.open();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, playlistId);
            ps.setString(2, songId);
            ps.setInt(3, songPosition);
            ps.executeUpdate();
        }
    }

    /**
     * Returns playlistSong item of a specific songId and playlistId
     * @param songId
     * @param playlistId
     * @return
     * @throws SQLException
     */
    public PlaylistSong getPlaylistSong(String songId, String playlistId) throws SQLException {
        String sql =
                "SELECT * FROM playlist_song "
                + "WHERE playlist_id=? AND song_id=?";
        try (Connection co = Connexion.open();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, playlistId);
            ps.setString(2, songId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapper(rs);
                }
                return null;
            }
        }
    }

    /**
     * Returns a playlist with all its songId references in order of positions
     * @param playlistId
     * @return
     * @throws SQLException
     */
    public ArrayList<String> getOrderedPlaylist(String playlistId) throws SQLException {
        ArrayList<String> orderedPlaylist = new ArrayList<>();

        // query that returns all songIds from a specific playlist in order
        String sql =
                "SELECT * FROM playlist_song "
                + "WHERE playlist_id=? "
                + "ORDER BY position ASC";
        try (Connection co = Connexion.open();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, playlistId);
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    orderedPlaylist.add(rs.getString("song_id"));
                }
                return orderedPlaylist;
            }
        }
    }

    /**
     * updates the position of a song in a playlist
     * @param songId
     * @param playlistId
     * @param songPosition
     * @throws SQLException
     */
    public void updatePlaylistSong(String songId, String playlistId, int songPosition) throws SQLException {
        String sql =
                "UPDATE playlist_song "
                + "SET position=?, updated_at=CURRENT_TIMESTAMP "
                + "WHERE playlist_id=? AND song_id=?";
        try (Connection co = Connexion.open();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setInt(1, songPosition);
            ps.setString(2, playlistId);
            ps.setString(3, songId);
            ps.executeUpdate();
        }
    }

    public void deletePlaylistSong(String songId, String playlistId) throws SQLException {
        String sql =
                "DELETE FROM playlist_song WHERE playlist_id=? AND song_id=?";
        try (Connection co = Connexion.open();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, playlistId);
            ps.setString(2, songId);
            ps.executeUpdate();
        }
    }

    /**
     * Transfomr a query result of SELECT * FROM playlist_song into a playlistSong object
     * @param rs
     * @return
     * @throws SQLException
     */
    private PlaylistSong mapper(ResultSet rs) throws SQLException {
        PlaylistSong ps = new PlaylistSong();
        ps.setPlaylistId(rs.getString("playlist_id"));
        ps.setSongId(rs.getString("song_id"));
        ps.setPosition(rs.getInt("position"));
        ps.setCreatedAt(rs.getDate("created_at").toLocalDate());
        ps.setUpdatedAt(rs.getDate("updated_at").toLocalDate());
        return ps;
    }
}
