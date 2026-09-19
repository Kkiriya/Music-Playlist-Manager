package com.maisonneuve.music_playlist_manager.dao;

import com.maisonneuve.music_playlist_manager.model.LibraryPlaylist;
import com.maisonneuve.music_playlist_manager.util.Connexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibraryPlaylistDAO {

    /**
     * Adds a playlist to a library
     *
     * @param lp
     * @throws SQLException
     */
    public void createLibraryPlaylist(LibraryPlaylist lp) throws SQLException {
        String sql = "INSERT INTO library_playlist "
                + "(library_id, playlist_id) "
                + "VALUES (?, ?)";

        try (
                Connection co = Connexion.open();
                PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, lp.getLibraryId());
            ps.setString(2, lp.getPlaylistId());

            ps.executeUpdate();
        }
    }

    /**
     * Intentionally kept empty because there is nothing to update
     * @param lp
     * @throws SQLException
     */
    public void updateLibraryPlaylist(LibraryPlaylist lp) throws SQLException {
        return;
    }

    /**
     * Deletes a playlist from a library
     * @param lp
     * @throws SQLException
     */
    public void deleteLibraryPlalyist(String libraryId, String playlistId) throws SQLException {
        String sql = "DELETE FROM library_playlist WHERE library_id=? AND playlist_id=?";

        try (
                Connection co = Connexion.open();
                PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, libraryId);
            ps.setString(2, playlistId);

            ps.executeUpdate();
        }
    }

    public LibraryPlaylist getLibraryPlaylist(String libraryId, String playlistId) throws SQLException {
        String sql = "SELECT * FROM library_playlist "
                + "WHERE library_id=? AND playlist_id=?";

        try (
                Connection co = Connexion.open();
                PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, libraryId);
            ps.setString(2, playlistId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapper(rs);
                }
                return  null;
            }
        }
    }

    private LibraryPlaylist mapper(ResultSet rs) throws SQLException {
        return new LibraryPlaylist(
                rs.getString("library_id"),
                rs.getString("playlist_id")
        );
    }
}
