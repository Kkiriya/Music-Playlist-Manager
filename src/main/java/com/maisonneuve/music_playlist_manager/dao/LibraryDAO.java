package com.maisonneuve.music_playlist_manager.dao;

import com.maisonneuve.music_playlist_manager.model.Library;
import com.maisonneuve.music_playlist_manager.util.Connexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibraryDAO {

    /**
     * Creates a new library with the given library object
     * @param l
     * @throws SQLException
     */
    public void createLibrary(Library l) throws SQLException {
        String sql = "INSERT INTO library "
                + "(library_id) "
                + "VALUES (?)";

        try (
                Connection co = Connexion.open();
                PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, l.getLibraryId());

            ps.executeUpdate();
        }
    }

    /**
     * No updates can realistically be done on library
     * @throws SQLException
     */
    public void updateLibrary() throws SQLException {
        return;
    }

    public void deleteLibrary(String libraryId) throws SQLException {
        String sql = "DELETE FROM library WHERE library_id=?";

        try (
                Connection co = Connexion.open();
                PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, libraryId);
            ps.executeUpdate();
        }
    }

    public Library getLibrary(String libraryId) throws SQLException {
        String sql = "SELECT * FROM library "
                + "WHERE library_id=?";

        try (
                Connection co = Connexion.open();
                PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, libraryId);
            ResultSet rs = ps.executeQuery();

            if (rs.getString("library_id").equals(libraryId)) {
                return new Library(libraryId);
            } else {
                throw new SQLException("Library not found");
            }
        }
    }
}
