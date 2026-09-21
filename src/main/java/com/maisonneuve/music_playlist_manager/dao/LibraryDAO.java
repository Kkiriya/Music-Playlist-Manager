package com.maisonneuve.music_playlist_manager.dao;

import com.maisonneuve.music_playlist_manager.model.Library;
import com.maisonneuve.music_playlist_manager.model.Playlist;
import com.maisonneuve.music_playlist_manager.util.Connexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LibraryDAO {

    /**
     * Creates a new library in the db
     * @param l
     * @throws SQLException
     */
    public void createLibrary(Library l) throws SQLException {
        String sql =
                "INSERT INTO library "
                + "(library_id) "
                + "VALUES (?)";
        try (Connection co = Connexion.open();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, l.getLibraryId());
            ps.executeUpdate();
        }
    }

    /**
     * Fetches a library from the db
     * Not much use for this rn really but once users are integrated it will be more usefull
     * @param libraryId
     */
    public Library getLibrary(String libraryId) throws SQLException {
        String sql =
                "SELECT * FROM library "
                + "WHERE library_id=?";
        try (Connection co = Connexion.open();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, libraryId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapper(rs);
                }
                return null;
            }
        }
    }

    /**
     * Updates the library in the db
     * Essentially just used to update the timestamp
     * @param l
     * @throws SQLException
     */
    public void updateLibrary(Library l)throws SQLException {
        String sql =
                "UPDATE library "
                + "SET updated_at=CURRENT_TIMESTAMP "
                + "WHERE library_id=?";
        try (Connection co = Connexion.open();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, l.getLibraryId());
            ps.executeUpdate();
        }
    }

    /**
     * Deletes a library from db
     * @param libraryId
     * @throws SQLException
     */
    public void deleteLibrary(String libraryId) throws SQLException {
        String sql =
                "DELETE FROM library WHERE library_id=?";
        try (Connection co = Connexion.open();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, libraryId);
            ps.executeUpdate();
        }
    }

    /**
     * Transform a query result of SELECT * FROM library into Library object
     * @param rs
     * @return
     * @throws SQLException
     */
    private Library mapper(ResultSet rs) throws SQLException {
        Library l = new Library();
        l.setLibraryId(rs.getString("library_id"));
        l.setCreatedAt(rs.getDate("created_at").toLocalDate());
        l.setUpdatedAt(rs.getDate("updated_at").toLocalDate());
        return l;
    }
}
