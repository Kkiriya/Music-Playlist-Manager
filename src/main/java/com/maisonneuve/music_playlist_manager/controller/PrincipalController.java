package com.maisonneuve.music_playlist_manager.controller;

import com.maisonneuve.music_playlist_manager.model.Genre;
import com.maisonneuve.music_playlist_manager.model.Song;
import com.maisonneuve.music_playlist_manager.util.CsvReader;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PrincipalController {
    @FXML
    private ComboBox<Genre> genreFilter;
    @FXML
    private ComboBox<String> decennieFilter;
    @FXML
    private ComboBox<String> sortCriterionChoice;
    @FXML
    private ComboBox<String> sortOrderChoice;
    @FXML
    private ComboBox<String> sortAlgorithmChoice;
    @FXML
    private ComboBox<String> pageSizeComboBox;
    @FXML
    private TableView<Song> tableSongList;
    @FXML
    private TableColumn<Song, String> titleColumn;
    @FXML
    private TableColumn<Song, String> artistColumn;
    @FXML
    private TableColumn<Song, Genre> genreColumn;
    @FXML
    private TableColumn<Song, String> releaseYearColumn;
    @FXML
    private TableColumn<Song, Integer> durationColumn;
    @FXML
    private TableColumn<Song, Integer> listenCountColumn;

    @FXML
    public void initialize() {
        setupSongTable();
        loadSongs();

        genreFilter.getItems().setAll(Genre.values());

        decennieFilter.getItems().setAll(
                "Toutes",
                "1970s",
                "1980s",
                "1990s",
                "2000s",
                "2010s",
                "2020s"
        );

        decennieFilter.setValue("Toutes");

        sortCriterionChoice.getItems().setAll(
                "Titre",
                "Artiste",
                "Durée",
                "Année",
                "Écoutes",
                "Genre"
        );
        sortCriterionChoice.setValue("Titre");

        sortOrderChoice.getItems().setAll("Croissant", "Décroissant");
        sortOrderChoice.setValue("Croissant");

        sortAlgorithmChoice.getItems().setAll(
                "Bubble sort",
                "Selection sort",
                "Insertion sort",
                "Merge sort",
                "Quick sort"
        );
        sortAlgorithmChoice.setValue("Bubble sort");

        pageSizeComboBox.getItems().setAll("10", "25", "50", "100");
        pageSizeComboBox.setValue("25");
    }

    /**
     * Links each table column to the matching Song getter.
     * new SimpleStringProperty(...) wraps that String in a JavaFX property.
     */
    private void setupSongTable() {
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

    private void loadSongs() {
        CsvReader csvReader = new CsvReader();
        tableSongList.getItems().setAll(csvReader.readSongs());
    }
}
