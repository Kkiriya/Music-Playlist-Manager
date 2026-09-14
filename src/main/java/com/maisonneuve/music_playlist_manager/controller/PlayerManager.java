package com.maisonneuve.music_playlist_manager.controller;

import com.maisonneuve.music_playlist_manager.model.Song;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerManager {
    private static final double PLAY_DURATION_SECONDS = 5.0;
    private static final double PROGRESS_MAX = 100.0;
    private static final double PROGRESS_STEP = 2.0;

    private final Label currentSongTitleLabel;
    private final Label currentSongArtistLabel;
    private final Button previousTrackButton;
    private final Button playPauseButton;
    private final Button nextTrackButton;
    private final Button shuffleButton;
    private final Slider playbackProgressSlider;
    private final Runnable onSongUpdated;
    private final Random random = new Random();
    private final Timeline playbackTimeline;
    private List<Song> songs = new ArrayList<>();
    private int currentIndex = -1;
    private boolean playing = false;
    private boolean shuffle = false;

    public PlayerManager(
            Label currentSongTitleLabel,
            Label currentSongArtistLabel,
            Button previousTrackButton,
            Button playPauseButton,
            Button nextTrackButton,
            Button shuffleButton,
            Slider playbackProgressSlider,
            Runnable onSongUpdated
    ) {
        this.currentSongTitleLabel = currentSongTitleLabel;
        this.currentSongArtistLabel = currentSongArtistLabel;
        this.previousTrackButton = previousTrackButton;
        this.playPauseButton = playPauseButton;
        this.nextTrackButton = nextTrackButton;
        this.shuffleButton = shuffleButton;
        this.playbackProgressSlider = playbackProgressSlider;
        this.onSongUpdated = onSongUpdated;
        this.playbackTimeline = createPlaybackTimeline();
    }

    public void setup() {
        playbackProgressSlider.setMin(0);
        playbackProgressSlider.setMax(PROGRESS_MAX);
        playbackProgressSlider.setValue(0);

        previousTrackButton.setOnAction(event -> previousSong());
        playPauseButton.setOnAction(event -> playCurrentSong());
        nextTrackButton.setOnAction(event -> nextSong());
        shuffleButton.setOnAction(event -> toggleShuffle());

        updatePlayer();
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs == null ? new ArrayList<>() : new ArrayList<>(songs);
        currentIndex = this.songs.isEmpty() ? -1 : 0;
        stopPlayback();
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

        startPlayback();
    }

    private void playCurrentSong() {
        if (currentIndex < 0 || currentIndex >= songs.size()) {
            return;
        }

        if (playing) {
            pausePlayback();
            return;
        }

        startPlayback();
    }

    private void startPlayback() {
        songs.get(currentIndex).incrementListenCount();
        onSongUpdated.run();
        playing = true;
        playbackProgressSlider.setValue(0);
        playbackTimeline.playFromStart();
        updatePlayer();
    }

    private void pausePlayback() {
        playing = false;
        playbackTimeline.pause();
        updatePlayer();
    }

    private void stopPlayback() {
        playing = false;
        playbackTimeline.stop();
        playbackProgressSlider.setValue(0);
    }

    private void previousSong() {
        if (songs.isEmpty()) {
            return;
        }

        currentIndex--;
        if (currentIndex < 0) {
            currentIndex = songs.size() - 1;
        }

        stopPlayback();
        updatePlayer();
    }

    private void nextSong() {
        if (songs.isEmpty()) {
            return;
        }

        if (shuffle) {
            currentIndex = getRandomSongIndex();
        } else {
            currentIndex = (currentIndex + 1) % songs.size();
        }

        startPlayback();
    }

    private void toggleShuffle() {
        shuffle = !shuffle;
        updatePlayer();
    }

    private int getRandomSongIndex() {
        if (songs.size() <= 1) {
            return 0;
        }

        int randomIndex;
        do {
            randomIndex = random.nextInt(songs.size());
        } while (randomIndex == currentIndex);

        return randomIndex;
    }

    private Timeline createPlaybackTimeline() {
        double interval = PLAY_DURATION_SECONDS / (PROGRESS_MAX / PROGRESS_STEP);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(interval), event -> updateProgress()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        return timeline;
    }

    private void updateProgress() {
        double newProgress = playbackProgressSlider.getValue() + PROGRESS_STEP;

        if (newProgress >= PROGRESS_MAX) {
            finishPlayback();
        } else {
            playbackProgressSlider.setValue(newProgress);
        }

        updatePlayer();
    }

    private void finishPlayback() {
        playing = false;
        playbackTimeline.stop();
        playbackProgressSlider.setValue(PROGRESS_MAX);
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
        playPauseButton.setText(playing ? "Pause" : "Play");
        shuffleButton.setText(shuffle ? "Shuffle on" : "Shuffle");
    }
}
