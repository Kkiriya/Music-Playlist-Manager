package com.maisonneuve.music_playlist_manager.dao;

import com.maisonneuve.music_playlist_manager.model.LibrarySongs;
import com.maisonneuve.music_playlist_manager.util.Connexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LibrarySongsDAO {

    /**
     * Effectively adds a song to the library in the db
     * @param songId
     * @param libraryId
     * @throws SQLException
     */
    public void createLibrarySongs(String songId, String libraryId) throws SQLException {
        String sql =
                "INSERT into library_songs "
                + "(library_id, song_id) "
                + "VALUES (? ,?)";
        try (Connection co = Connexion.open();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, libraryId);
            ps.setString(2, songId);
            ps.executeUpdate();
        }
    }

    /**
     * Returns a LibrarySongs object
     * aka the litteral line in the db
     * @param songId
     * @param libraryId
     * @return
     * @throws SQLException
     */
    public LibrarySongs getLibrarySongs(String songId, String libraryId) throws SQLException {
        String sql =
                "SELECT * FROM library_songs "
                + "WHERE library_id=? AND song_id=?";
        try (Connection co = Connexion.open();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, libraryId);
            ps.setString(2, songId);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return mapper(rs);
                }
                return null;
            }
        }
    }

    /**
     * Returns a list of all songIds in the given library
     * aka the actual songs in that library
     * @param libraryId
     * @return
     * @throws SQLException
     */
    public ArrayList<String> getLibrarySongsIds(String libraryId) throws SQLException {
        ArrayList<String> songIds = new ArrayList<>();

        String sql =
                "SELECT * FROM library "
                        + "WHERE library_id=?";
        try (Connection co = Connexion.open();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, libraryId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    songIds.add(rs.getString("song_id"));
                }
                return songIds;
            }
        }
    }

    /**
     * Effectively updates the timestamp
     * @param songId
     * @param libraryId
     * @throws SQLException
     */
    public void updateLibrarySongs(String songId, String libraryId) throws SQLException {
        String sql =
                "UPDATE library_songs "
                + "SET updated_at=CURRENT_TIMESTAMP "
                + "WHERE library_id=? AND song_id=?";
        try (Connection co = Connexion.open();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, libraryId);
            ps.setString(2, songId);
            ps.executeUpdate();
        }
    }

    /**
     * Deletes a librarySong from db
     * @param songId
     * @param libraryId
     * @throws SQLException
     */
    public void deleteLibrarySongs(String songId, String libraryId) throws SQLException {
        String sql =
                "DELETE FROM library_songs WHERE library_id=? AND song_id=?";
        try (Connection co = Connexion.open();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, libraryId);
            ps.setString(2, songId);
            ps.executeUpdate();
        }
    }

    /**
     * Transfroms query results into LibrarySongs object
     * @param rs
     * @return
     * @throws SQLException
     */
    private LibrarySongs mapper(ResultSet rs) throws SQLException {
        LibrarySongs ls = new LibrarySongs();
        ls.setLibraryId(rs.getString("library_id"));
        ls.setSongId(rs.getString("song_id"));
        ls.setCreatedAt(rs.getDate("created_at").toLocalDate());
        ls.setUpdatedAt(rs.getDate("updated_at").toLocalDate());
        return ls;
    }
}
