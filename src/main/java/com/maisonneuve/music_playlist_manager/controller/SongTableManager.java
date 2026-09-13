package com.maisonneuve.music_playlist_manager.controller;

import com.maisonneuve.music_playlist_manager.model.Genre;
import com.maisonneuve.music_playlist_manager.model.Song;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;

public class SongTableManager {
    private final TableColumn<Song, String> titleColumn;
    private final TableColumn<Song, String> artistColumn;
    private final TableColumn<Song, Genre> genreColumn;
    private final TableColumn<Song, String> releaseYearColumn;
    private final TableColumn<Song, Integer> durationColumn;
    private final TableColumn<Song, Integer> listenCountColumn;

    public SongTableManager(
            TableColumn<Song, String> titleColumn,
            TableColumn<Song, String> artistColumn,
            TableColumn<Song, Genre> genreColumn,
            TableColumn<Song, String> releaseYearColumn,
            TableColumn<Song, Integer> durationColumn,
            TableColumn<Song, Integer> listenCountColumn
    ) {
        this.titleColumn = titleColumn;
        this.artistColumn = artistColumn;
        this.genreColumn = genreColumn;
        this.releaseYearColumn = releaseYearColumn;
        this.durationColumn = durationColumn;
        this.listenCountColumn = listenCountColumn;
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
                new SimpleIntegerProperty(cellData.getValue().getDuration()).asObject());
        listenCountColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getListenCount()).asObject());
    }
}
