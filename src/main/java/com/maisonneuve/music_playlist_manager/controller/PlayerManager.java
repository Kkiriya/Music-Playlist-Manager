package com.maisonneuve.music_playlist_manager.controller;

import com.maisonneuve.music_playlist_manager.model.Song;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerManager {
    private final Label currentSongTitleLabel;
    private final Label currentSongArtistLabel;
    private final Button previousTrackButton;
    private final Button playPauseButton;
    private final Button nextTrackButton;
    private final Button shuffleButton;
    private final Runnable onSongUpdated;
    private final Random random = new Random();
    private List<Song> songs = new ArrayList<>();
    private int currentIndex = -1;
    private boolean shuffle = false;

    public PlayerManager(
            Label currentSongTitleLabel,
            Label currentSongArtistLabel,
            Button previousTrackButton,
            Button playPauseButton,
            Button nextTrackButton,
            Button shuffleButton,
            Runnable onSongUpdated
    ) {
        this.currentSongTitleLabel = currentSongTitleLabel;
        this.currentSongArtistLabel = currentSongArtistLabel;
        this.previousTrackButton = previousTrackButton;
        this.playPauseButton = playPauseButton;
        this.nextTrackButton = nextTrackButton;
        this.shuffleButton = shuffleButton;
        this.onSongUpdated = onSongUpdated;
    }

    public void setup() {
        previousTrackButton.setOnAction(event -> previousSong());
        playPauseButton.setOnAction(event -> playCurrentSong());
        nextTrackButton.setOnAction(event -> nextSong());
        shuffleButton.setOnAction(event -> toggleShuffle());

        updatePlayer();
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs == null ? new ArrayList<>() : new ArrayList<>(songs);
        currentIndex = this.songs.isEmpty() ? -1 : 0;
        updatePlayer();
    }

    public void playSong(Song song) {
        if (song == null) {
            return;
        }

        int selectedIndex = songs.indexOf(song);
        if (selectedIndex >= 0) {
            currentIndex = selectedIndex;
        }

        song.incrementListenCount();
        onSongUpdated.run();
        updatePlayer();
    }

    private void playCurrentSong() {
        if (currentIndex < 0 || currentIndex >= songs.size()) {
            return;
        }

        songs.get(currentIndex).incrementListenCount();
        onSongUpdated.run();
        updatePlayer();
    }

    private void previousSong() {
        if (songs.isEmpty()) {
            return;
        }

        currentIndex--;
        if (currentIndex < 0) {
            currentIndex = songs.size() - 1;
        }

        updatePlayer();
    }

    private void nextSong() {
        if (songs.isEmpty()) {
            return;
        }

        // Next simulates one listen on the current song.
        songs.get(currentIndex).incrementListenCount();

        if (shuffle) {
            currentIndex = random.nextInt(songs.size());
        } else {
            currentIndex = (currentIndex + 1) % songs.size();
        }

        onSongUpdated.run();
        updatePlayer();
    }

    private void toggleShuffle() {
        shuffle = !shuffle;
        updatePlayer();
    }

    private void updatePlayer() {
        if (currentIndex < 0 || currentIndex >= songs.size()) {
            currentSongTitleLabel.setText("Aucune chanson en cours");
            currentSongArtistLabel.setText("Artiste: -");
            playPauseButton.setText("Play");
            shuffleButton.setText("Shuffle");
            return;
        }

        Song currentSong = songs.get(currentIndex);
        currentSongTitleLabel.setText(currentSong.getTitle());
        currentSongArtistLabel.setText("Artiste: " + currentSong.getArtist());
        playPauseButton.setText("Play");
        shuffleButton.setText(shuffle ? "Shuffle on" : "Shuffle");
    }
}
