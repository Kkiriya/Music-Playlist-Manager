package com.maisonneuve.music_playlist_manager.controller;

import com.maisonneuve.music_playlist_manager.model.Genre;
import com.maisonneuve.music_playlist_manager.model.Song;
import com.maisonneuve.music_playlist_manager.util.DurationFormatter;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

import java.util.function.Consumer;

public class SongTableManager {
    private final TableColumn<Song, String> titleColumn;
    private final TableColumn<Song, String> artistColumn;
    private final TableColumn<Song, Genre> genreColumn;
    private final TableColumn<Song, String> releaseYearColumn;
    private final TableColumn<Song, String> durationColumn;
    private final TableColumn<Song, Integer> listenCountColumn;
    private final TableColumn<Song, Void> playColumn;
    private final Consumer<Song> onPlaySong;

    public SongTableManager(
            TableColumn<Song, String> titleColumn,
            TableColumn<Song, String> artistColumn,
            TableColumn<Song, Genre> genreColumn,
            TableColumn<Song, String> releaseYearColumn,
            TableColumn<Song, String> durationColumn,
            TableColumn<Song, Integer> listenCountColumn,
            TableColumn<Song, Void> playColumn,
            Consumer<Song> onPlaySong
    ) {
        this.titleColumn = titleColumn;
        this.artistColumn = artistColumn;
        this.genreColumn = genreColumn;
        this.releaseYearColumn = releaseYearColumn;
        this.durationColumn = durationColumn;
        this.listenCountColumn = listenCountColumn;
        this.playColumn = playColumn;
        this.onPlaySong = onPlaySong;
    }

    /**
     * Links each table column to the matching Song getter.
     */
    public void setupColumns() {
        titleColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTitle()));
        artistColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getArtist()));
        genreColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getGenre()));
        releaseYearColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getReleaseDate()));
        durationColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(DurationFormatter.format(cellData.getValue().getDuration())));
        listenCountColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getListenCount()).asObject());
        setupPlayButtonColumn();
    }

    private void setupPlayButtonColumn() {
        playColumn.setCellFactory(column -> new TableCell<>() {
            private final Button playButton = new Button("Play");

            {
                playButton.setOnAction(event -> {
                    Song song = getTableView().getItems().get(getIndex());
                    onPlaySong.accept(song);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : playButton);
            }
        });
    }
}
