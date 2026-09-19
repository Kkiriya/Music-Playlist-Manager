package com.maisonneuve.music_playlist_manager.dao;

import com.maisonneuve.music_playlist_manager.model.LibraryPlaylist;
import com.maisonneuve.music_playlist_manager.model.LibrarySongs;
import com.maisonneuve.music_playlist_manager.util.Connexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibrarySongsDAO {

    /**
     * Effectively adds a song to the library
     * @param ls
     * @throws SQLException
     */
    public void createLibrarySongs(LibrarySongs ls) throws SQLException {
        String sql = "INSERT INTO library_songs "
                + "(library_id, song_id) "
                + "VALUES (?, ?)";

        try (
                Connection co = Connexion.open();
                PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, ls.getLibraryId());
            ps.setString(2, ls.getSongId());

            ps.executeUpdate();
        }
    }

    /**
     * Intentionally kept empty because there is nothing to update
     * @param ls
     * @throws SQLException
     */
    public void updateLibrarySongs(LibrarySongs ls) throws SQLException {
        return;
    }

    public void deletePlaylist(LibrarySongs ls) throws SQLException {
        String  sql = "DELETE FROM library_songs WHERE library_id=? AND playlist_id=?";

        try (
                Connection co = Connexion.open();
                PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, ls.getLibraryId());
            ps.setString(2, ls.getSongId());

            ps.executeUpdate();
        }
    }

    public LibrarySongs getLibrarySongs(String libraryId, String song_id) throws SQLException {
        String sql = "SELECT * FROM library_songs "
                + "WHERE library_id=? AND song_id=?";

        try (
                Connection co = Connexion.open();
                PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, libraryId);
            ps.setString(2, song_id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapper(rs);
                }
                return null;
            }
        }
    }

    private LibrarySongs mapper(ResultSet rs) throws SQLException {
        return new LibrarySongs(
                rs.getString("library_id"),
                rs.getString("song_id")
        );
    }
}
