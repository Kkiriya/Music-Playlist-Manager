package com.maisonneuve.music_playlist_manager.dao;

import com.maisonneuve.music_playlist_manager.model.LibraryPlaylist;
import com.maisonneuve.music_playlist_manager.model.PlaylistSong;
import com.maisonneuve.music_playlist_manager.util.Connexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibraryPlaylistDAO {

    /**
     * Creates a new relation between a library and a playlist in the db
     * @param libraryId
     * @param playlistId
     * @throws SQLException
     */
    public void createLibraryPlaylist(String libraryId, String playlistId) throws SQLException {
        String sql =
                "INSERT INTO library_playlist "
                + "(library_id, playlist_id) "
                + "VALUES (?, ?)";
        try (Connection co = Connexion.open();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, libraryId);
            ps.setString(2, playlistId);
            ps.executeUpdate();
        }
    }

    /**
     * Returns a library playlist
     * @param libraryId
     * @param playlistId
     * @return
     * @throws SQLException
     */
    public LibraryPlaylist getLibraryPlaylist(String libraryId, String playlistId) throws SQLException {
        String sql =
                "SELECT * FROM library_playlist "
                + "WHERE library_id=? AND playlist_id=?";
        try (Connection co = Connexion.open();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, libraryId);
            ps.setString(2, playlistId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapper(rs);
                }
                return null;
            }
        }
    }

    /**
     * Essentially just updates the timestamp
     * @param libraryId
     * @param playlistId
     * @throws SQLException
     */
    public void updateLibraryPlaylist(String libraryId, String playlistId) throws SQLException {
        String sql =
                "UPDATE library_playlist "
                + "SET updated_at=CURRENT_TIMESTAMP "
                + "WHERE library_id=? AND playlist_id=?";
        try (Connection co = Connexion.open();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, libraryId);
            ps.setString(2, playlistId);
            ps.executeUpdate();
        }
    }

    /**
     * Deletes a libraryPLaylist from db
     * @param libraryId
     * @param playlistId
     * @throws SQLException
     */
    public void deleteLibraryPlaylist(String libraryId, String playlistId) throws SQLException {
        String sql =
                "DELETE FROM library_playlist WHERE library_id=? AND playlist_id=?";
        try (Connection co = Connexion.open();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, libraryId);
            ps.setString(2, playlistId);
            ps.executeUpdate();
        }
    }

    /**
     * Transforms query results into LibraryPlaylist object
     * @param rs
     * @return
     * @throws SQLException
     */
    private LibraryPlaylist mapper(ResultSet rs) throws SQLException {
        LibraryPlaylist lp = new LibraryPlaylist();
        lp.setLibraryId(rs.getString("library_id"));
        lp.setPlaylistId(rs.getString("playlist_id"));
        lp.setCreatedAt(rs.getDate("created_at").toLocalDate());
        lp.setUpdatedAt(rs.getDate("updated_at").toLocalDate());
        return lp;
    }
}
