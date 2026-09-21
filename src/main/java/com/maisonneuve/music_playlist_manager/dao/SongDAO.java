package com.maisonneuve.music_playlist_manager.dao;

import com.maisonneuve.music_playlist_manager.model.Genre;
import com.maisonneuve.music_playlist_manager.model.Song;
import com.maisonneuve.music_playlist_manager.util.Connexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This specific table serves as the priamry datasource for all the songs available
 * for that reason it does not have the Create, Update and Delete operation of the CRUD
 */
public class SongDAO {
    /**
     * Returns the song with the corresponding id
     * @param songId
     */
    public Song getSongById(String songId) throws SQLException {
        String sql =
                "SELECT * FROM song "
                + "WHERE song_id=?";
        try (Connection co = Connexion.open();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, songId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapper(rs);
                }
                return null;
            }
        }
    }

    /**
     * Returns the song with the corresponding title
     * For now doesnt support cas insensitive and or progressive search
     * @param title
     * @return
     * @throws SQLException
     */
    public Song getSongByTitle(String title) throws SQLException {
        String sql =
                "SELECT * FROM song "
                + "WHERE title=?";
        try (Connection co = Connexion.open();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, title);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapper(rs);
                }
                return null;
            }
        }
    }

    /**
     * Returns a list containing all songs available in the catalogue
     * @return
     * @throws SQLException
     */
    public List<Song> getAllSongs() throws SQLException {
        List<Song> songs = new ArrayList<>();

        String sql =
                "SELECT * FROM song ";
        try (Connection co = Connexion.open();
             PreparedStatement ps = co.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    songs.add(mapper(rs));
                }
                return songs;
            }
        }
    }

    /**
     * Raise the listen count of the given song by 1
     * @param songId
     * @throws SQLException
     */
    public void raiseListenCount(String songId) throws SQLException {
        String sql =
                "UPDATE song "
                + "SET listen_count=listen_count+1, updated_at=CURRENT_TIMESTAMP "
                + "WHERE song_id=?";
        try (Connection co = Connexion.open();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, songId);
            ps.executeUpdate();
        }
    }

     /**
     * Transform a result query of SELECT * FROM SONG into appropriate Song object
     * @param rs
     * @return
     * @throws SQLException
     */
    private Song mapper(ResultSet rs) throws SQLException {
        Song s = new Song();
        s.setSongId(rs.getString("song_id"));
        s.setTitle(rs.getString("title"));
        s.setArtist(rs.getString("artist"));
        s.setAlbum(rs.getString("album"));
        s.setReleaseYear(rs.getInt("release_year"));
        s.setGenre(mapGenre(rs.getString("genre")));
        s.setDurationSeconds(rs.getInt("duration_seconds"));
        s.setListenCount(rs.getInt("listen_count"));
        s.setCreatedAt(rs.getDate("created_at").toLocalDate());
        s.setUpdatedAt(rs.getDate("updated_at").toLocalDate());
        return s;
    }

    private Genre mapGenre(String value) {
        if (value == null || value.isBlank()) {
            return Genre.POP;
        }

        String normalizedValue = value.trim().toLowerCase();

        for (Genre genre : Genre.values()) {
            if (genre.toString().equalsIgnoreCase(normalizedValue)) {
                return genre;
            }
        }

        String firstGenre = normalizedValue.split(",")[0].trim();
        for (Genre genre : Genre.values()) {
            if (genre.toString().equalsIgnoreCase(firstGenre)) {
                return genre;
            }
        }

        for (Genre genre : Genre.values()) {
            if (normalizedValue.contains(genre.toString().toLowerCase())) {
                return genre;
            }
        }

        return Genre.POP;
    }
}
