package com.maisonneuve.music_playlist_manager.controller;

import com.maisonneuve.music_playlist_manager.model.Genre;
import com.maisonneuve.music_playlist_manager.model.Song;
import com.maisonneuve.music_playlist_manager.util.CsvReader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;

public class PrincipalController {
    private PaginationManager paginationManager;
    private SongSortManager songSortManager;
    private SongFilterManager songFilterManager;
    private PlayerManager playerManager;
    private SongDetailManager songDetailManager;

    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> genreFilter;
    @FXML
    private ComboBox<String> decennieFilter;
    @FXML
    private ComboBox<String> artistFilter;
    @FXML
    private ComboBox<String> sortCriterionChoice;
    @FXML
    private ComboBox<String> sortOrderChoice;
    @FXML
    private ComboBox<String> sortAlgorithmChoice;
    @FXML
    private ComboBox<String> pageSizeComboBox;
    @FXML
    private Slider maxDurationSlider;
    @FXML
    private Slider minListenCountSlider;
    @FXML
    private Button resetFiltersButton;
    @FXML
    private Button sortButton;
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
    private TableColumn<Song, String> durationColumn;
    @FXML
    private TableColumn<Song, Integer> listenCountColumn;
    @FXML
    private TableColumn<Song, Void> playColumn;
    @FXML
    private Label selectedSongTitleLabel;
    @FXML
    private Label selectedSongArtistLabel;
    @FXML
    private Label selectedSongAlbumLabel;
    @FXML
    private Label selectedSongGenreLabel;
    @FXML
    private Label selectedSongReleaseYearLabel;
    @FXML
    private Label selectedSongDurationLabel;
    @FXML
    private Label selectedSongListenCountLabel;
    @FXML
    private Label currentSongTitleLabel;
    @FXML
    private Label currentSongArtistLabel;
    @FXML
    private Button previousTrackButton;
    @FXML
    private Button playPauseButton;
    @FXML
    private Button nextTrackButton;
    @FXML
    private Button shuffleButton;
    @FXML
    private Slider playbackProgressSlider;

    @FXML
    public void initialize() {
        setupDetailManager();
        setupPaginationManager();
        setupSortManager();
        setupPlayerManager();
        setupTableManager();
        setupFilterManager();
        setupFilters();
        setupSortChoices();
        setupPageSizeChoices();
        loadSongs();
    }

    private void setupTableManager() {
        SongTableManager songTableManager = new SongTableManager(
                titleColumn,
                artistColumn,
                genreColumn,
                releaseYearColumn,
                durationColumn,
                listenCountColumn,
                playColumn,
                song -> playerManager.playSong(song)
        );
        songTableManager.setupColumns();
    }

    private void setupDetailManager() {
        songDetailManager = new SongDetailManager(
                tableSongList,
                selectedSongTitleLabel,
                selectedSongArtistLabel,
                selectedSongAlbumLabel,
                selectedSongGenreLabel,
                selectedSongReleaseYearLabel,
                selectedSongDurationLabel,
                selectedSongListenCountLabel
        );
        songDetailManager.setupSelectionListener();
    }

    private void setupPaginationManager() {
        paginationManager = new PaginationManager(
                tableSongList,
                btnPrevious,
                btnNext,
                lblPage,
                pageSizeComboBox
        );
        paginationManager.setup();
    }

    private void setupSortManager() {
        songSortManager = new SongSortManager(
                sortCriterionChoice,
                sortOrderChoice,
                sortAlgorithmChoice,
                sortButton,
                sortedSongs -> paginationManager.setSongs(sortedSongs)
        );
        songSortManager.setup();
    }

    private void setupFilterManager() {
        songFilterManager = new SongFilterManager(
                searchField,
                genreFilter,
                decennieFilter,
                artistFilter,
                maxDurationSlider,
                minListenCountSlider,
                resetFiltersButton,
                filteredSongs -> {
                    songSortManager.setSongs(filteredSongs);
                    playerManager.setSongs(filteredSongs);
                    paginationManager.setSongs(filteredSongs);
                }
        );
        songFilterManager.setup();
    }

    private void setupPlayerManager() {
        playerManager = new PlayerManager(
                currentSongTitleLabel,
                currentSongArtistLabel,
                previousTrackButton,
                playPauseButton,
                nextTrackButton,
                shuffleButton,
                playbackProgressSlider,
                () -> {
                    paginationManager.refresh();
                    songDetailManager.refreshSelectedSong();
                }
        );
        playerManager.setup();
    }

    private void setupFilters() {
        genreFilter.getItems().setAll("Tous");
        for (Genre genre : Genre.values()) {
            genreFilter.getItems().add(genre.toString());
        }
        genreFilter.setValue("Tous");

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
    }

    private void setupSortChoices() {
        sortCriterionChoice.getItems().setAll(
                "Titre",
                "Artiste",
                "Duree",
                "Annee",
                "Ecoutes",
                "Genre"
        );
        sortCriterionChoice.setValue("Titre");

        sortOrderChoice.getItems().setAll("Croissant", "Decroissant");
        sortOrderChoice.setValue("Croissant");

        sortAlgorithmChoice.getItems().setAll(
                "Bubble sort",
                "Selection sort",
                "Insertion sort",
                "Merge sort",
                "Quick sort"
        );
        sortAlgorithmChoice.setValue("Bubble sort");
    }

    private void setupPageSizeChoices() {
        pageSizeComboBox.getItems().setAll("10", "25", "50", "100");
        pageSizeComboBox.setValue("25");
    }

    private void loadSongs() {
        CsvReader csvReader = new CsvReader();
        List<Song> songs = csvReader.readSongs();
        songFilterManager.setSongs(songs);
    }
}
