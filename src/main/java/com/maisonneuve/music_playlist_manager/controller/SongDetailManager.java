package com.maisonneuve.music_playlist_manager.controller;

import com.maisonneuve.music_playlist_manager.model.Song;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class SongDetailManager {
    private final TableView<Song> tableSongList;
    private final Label selectedSongTitleLabel;
    private final Label selectedSongArtistLabel;
    private final Label selectedSongAlbumLabel;
    private final Label selectedSongGenreLabel;
    private final Label selectedSongReleaseYearLabel;
    private final Label selectedSongDurationLabel;
    private final Label selectedSongListenCountLabel;

    public SongDetailManager(
            TableView<Song> tableSongList,
            Label selectedSongTitleLabel,
            Label selectedSongArtistLabel,
            Label selectedSongAlbumLabel,
            Label selectedSongGenreLabel,
            Label selectedSongReleaseYearLabel,
            Label selectedSongDurationLabel,
            Label selectedSongListenCountLabel
    ) {
        this.tableSongList = tableSongList;
        this.selectedSongTitleLabel = selectedSongTitleLabel;
        this.selectedSongArtistLabel = selectedSongArtistLabel;
        this.selectedSongAlbumLabel = selectedSongAlbumLabel;
        this.selectedSongGenreLabel = selectedSongGenreLabel;
        this.selectedSongReleaseYearLabel = selectedSongReleaseYearLabel;
        this.selectedSongDurationLabel = selectedSongDurationLabel;
        this.selectedSongListenCountLabel = selectedSongListenCountLabel;
    }

    /**
     * Updates the detail panel when a song is selected.
     */
    public void setupSelectionListener() {
        tableSongList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldSong, selectedSong) -> showSongDetails(selectedSong)
        );
    }

    private void showSongDetails(Song song) {
        if (song == null) {
            selectedSongTitleLabel.setText("Aucune chanson");
            selectedSongArtistLabel.setText("Artiste: -");
            selectedSongAlbumLabel.setText("Album: -");
            selectedSongGenreLabel.setText("Genre: -");
            selectedSongReleaseYearLabel.setText("Annee: -");
            selectedSongDurationLabel.setText("Duree: -");
            selectedSongListenCountLabel.setText("Ecoutes: -");
            return;
        }

        selectedSongTitleLabel.setText(song.getTitle());
        selectedSongArtistLabel.setText("Artiste: " + song.getArtist());
        selectedSongAlbumLabel.setText("Album: " + song.getAlbum());
        selectedSongGenreLabel.setText("Genre: " + song.getGenre());
        selectedSongReleaseYearLabel.setText("Annee: " + song.getReleaseDate());
        selectedSongDurationLabel.setText("Duree: " + formatDuration(song.getDuration()));
        selectedSongListenCountLabel.setText("Ecoutes: " + song.getListenCount());
    }

    private String formatDuration(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
}
