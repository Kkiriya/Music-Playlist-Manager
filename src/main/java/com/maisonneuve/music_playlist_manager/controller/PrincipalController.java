package com.maisonneuve.music_playlist_manager.controller;

import com.maisonneuve.music_playlist_manager.model.Genre;
import com.maisonneuve.music_playlist_manager.model.Song;
import com.maisonneuve.music_playlist_manager.util.CsvReader;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;

public class PrincipalController {
    private List<Song> allSongs = new ArrayList<>();
    private int currentPage = 1;
    private int pageSize = 25;

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
    private Button btnPrevious;
    @FXML
    private Button btnNext;
    @FXML
    private Label lblPage;
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
        setupPagination();

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

        loadSongs();
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
        allSongs = csvReader.readSongs();
        currentPage = 1;
        updatePage();
    }

    /**
     * Connects the pagination buttons and page size choice.
     */
    private void setupPagination() {
        btnPrevious.setOnAction(event -> previousPage());
        btnNext.setOnAction(event -> nextPage());

        pageSizeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }

            pageSize = Integer.parseInt(newValue);
            currentPage = 1;
            updatePage();
        });
    }

    private void previousPage() {
        if (currentPage > 1) {
            currentPage--;
            updatePage();
        }
    }

    private void nextPage() {
        if (currentPage < getTotalPages()) {
            currentPage++;
            updatePage();
        }
    }

    private void updatePage() {
        int totalPages = getTotalPages();
        int fromIndex = (currentPage - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, allSongs.size());

        if (allSongs.isEmpty()) {
            tableSongList.getItems().clear();
        } else {
            tableSongList.getItems().setAll(allSongs.subList(fromIndex, toIndex));
        }

        lblPage.setText("Page " + currentPage + " / " + totalPages);
        btnPrevious.setDisable(currentPage <= 1);
        btnNext.setDisable(currentPage >= totalPages);
    }

    private int getTotalPages() {
        if (allSongs.isEmpty()) {
            return 1;
        }

        return (int) Math.ceil((double) allSongs.size() / pageSize);
    }
}
