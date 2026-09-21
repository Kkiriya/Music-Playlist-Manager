package com.maisonneuve.music_playlist_manager.controller;

import com.maisonneuve.music_playlist_manager.model.Genre;
import com.maisonneuve.music_playlist_manager.model.Playlist;
import com.maisonneuve.music_playlist_manager.model.Song;
import com.maisonneuve.music_playlist_manager.services.PlaylistService;
import com.maisonneuve.music_playlist_manager.services.SongService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PrincipalController {
    private PaginationManager paginationManager;
    private SongSortManager songSortManager;
    private SongFilterManager songFilterManager;
    private PlayerManager playerManager;
    private SongDetailManager songDetailManager;
    private final SongService songService = new SongService();
    private final PlaylistService playlistService = new PlaylistService();

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
    private Button benchmarkButton;
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
    private Button addSelectedSongToPlaylistButton;
    @FXML
    private ListView<Playlist> playlistListView;
    @FXML
    private Button createPlaylistButton;
    @FXML
    private Button renamePlaylistButton;
    @FXML
    private Button deletePlaylistButton;
    @FXML
    private Button viewPlaylistButton;
    @FXML
    private Button removeSelectedSongFromPlaylistButton;
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
        setupBenchmarkButton();
        setupPlaylistControls();
        setupTableManager();
        setupFilterManager();
        setupFilters();
        setupSortChoices();
        setupPageSizeChoices();
        loadSongs();
        loadPlaylists();
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

    private void setupBenchmarkButton() {
        benchmarkButton.setOnAction(event -> openBenchmarkWindow());
    }

    private void setupPlaylistControls() {
        createPlaylistButton.setOnAction(event -> createPlaylist());
        renamePlaylistButton.setOnAction(event -> renameSelectedPlaylist());
        deletePlaylistButton.setOnAction(event -> deleteSelectedPlaylist());
        viewPlaylistButton.setOnAction(event -> viewSelectedPlaylist());
        addSelectedSongToPlaylistButton.setOnAction(event -> addSelectedSongToSelectedPlaylist());
        removeSelectedSongFromPlaylistButton.setOnAction(event -> removeSelectedSongFromSelectedPlaylist());

        playlistListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldPlaylist, selectedPlaylist) -> updatePlaylistButtonStates()
        );
        tableSongList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldSong, selectedSong) -> updatePlaylistButtonStates()
        );
        updatePlaylistButtonStates();
    }

    private void updatePlaylistButtonStates() {
        boolean noPlaylistSelected = playlistListView.getSelectionModel().getSelectedItem() == null;
        boolean noSongSelected = tableSongList.getSelectionModel().getSelectedItem() == null;

        renamePlaylistButton.setDisable(noPlaylistSelected);
        deletePlaylistButton.setDisable(noPlaylistSelected);
        viewPlaylistButton.setDisable(noPlaylistSelected);
        addSelectedSongToPlaylistButton.setDisable(noPlaylistSelected || noSongSelected);
        removeSelectedSongFromPlaylistButton.setDisable(noPlaylistSelected || noSongSelected);
    }

    private void createPlaylist() {
        Optional<String> name = askPlaylistName("Nouvelle playlist", "");
        if (name.isEmpty()) {
            return;
        }

        try {
            Playlist playlist = new Playlist();
            playlist.setName(name.get());
            playlistService.createPlaylist(playlist);
            loadPlaylists();
            playlistListView.getSelectionModel().select(playlist);
        } catch (SQLException | IllegalArgumentException exception) {
            showError(exception.getMessage());
        }
    }

    private void renameSelectedPlaylist() {
        Playlist selectedPlaylist = getSelectedPlaylistOrShowError();
        if (selectedPlaylist == null) {
            return;
        }

        Optional<String> name = askPlaylistName("Modifier playlist", selectedPlaylist.getName());
        if (name.isEmpty()) {
            return;
        }

        try {
            selectedPlaylist.setName(name.get());
            playlistService.updatePlaylist(selectedPlaylist);
            loadPlaylists();
            playlistListView.getSelectionModel().select(selectedPlaylist);
        } catch (SQLException | IllegalArgumentException exception) {
            showError(exception.getMessage());
        }
    }

    private void deleteSelectedPlaylist() {
        Playlist selectedPlaylist = getSelectedPlaylistOrShowError();
        if (selectedPlaylist == null || !confirm("Supprimer la playlist \"" + selectedPlaylist.getName() + "\" ?")) {
            return;
        }

        try {
            playlistService.deletePlaylist(selectedPlaylist.getPlaylistId());
            loadPlaylists();
        } catch (SQLException | IllegalArgumentException exception) {
            showError(exception.getMessage());
        }
    }

    private void viewSelectedPlaylist() {
        Playlist selectedPlaylist = getSelectedPlaylistOrShowError();
        if (selectedPlaylist == null) {
            return;
        }

        try {
            List<Song> songs = playlistService.getPlaylistSongs(selectedPlaylist.getPlaylistId());
            StringBuilder content = new StringBuilder();
            if (songs.isEmpty()) {
                content.append("Aucune chanson dans cette playlist.");
            } else {
                for (Song song : songs) {
                    content.append("- ")
                            .append(song.getTitle())
                            .append(" / ")
                            .append(song.getArtist())
                            .append("\n");
                }
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Playlist");
            alert.setHeaderText(selectedPlaylist.getName());
            alert.setContentText(content.toString());
            alert.showAndWait();
        } catch (SQLException | IllegalArgumentException exception) {
            showError(exception.getMessage());
        }
    }

    private void addSelectedSongToSelectedPlaylist() {
        Playlist playlist = getSelectedPlaylistOrShowError();
        Song song = getSelectedSongOrShowError();
        if (playlist == null || song == null) {
            return;
        }

        try {
            playlistService.addSongToPlaylist(song.getSongId(), playlist.getPlaylistId());
            loadPlaylists();
        } catch (SQLException | IllegalArgumentException exception) {
            showError(exception.getMessage());
        }
    }

    private void removeSelectedSongFromSelectedPlaylist() {
        Playlist playlist = getSelectedPlaylistOrShowError();
        Song song = getSelectedSongOrShowError();
        if (playlist == null || song == null) {
            return;
        }

        try {
            playlistService.removeSongFromPlaylist(song.getSongId(), playlist.getPlaylistId());
            loadPlaylists();
        } catch (SQLException | IllegalArgumentException exception) {
            showError(exception.getMessage());
        }
    }

    private void openBenchmarkWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/maisonneuve/music_playlist_manager/views/lab.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());
            var stylesheet = getClass().getResource("/com/maisonneuve/music_playlist_manager/styles/theme.css");
            if (stylesheet != null) {
                scene.getStylesheets().add(stylesheet.toExternalForm());
            }

            stage.setTitle("Benchmark");
            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            throw new IllegalStateException("Impossible d'ouvrir la fenetre de benchmark.", exception);
        }
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
        try {
            List<Song> songs = songService.getAllSongs();
            songFilterManager.setSongs(songs);
        } catch (SQLException exception) {
            songFilterManager.setSongs(new ArrayList<>());
            showError("Impossible de charger les chansons depuis la base de donnees.");
        }
    }

    private void loadPlaylists() {
        Playlist selectedPlaylist = playlistListView.getSelectionModel().getSelectedItem();

        try {
            List<Playlist> playlists = playlistService.getAllPlaylists();
            playlistListView.getItems().setAll(playlists);

            if (selectedPlaylist != null) {
                for (Playlist playlist : playlists) {
                    if (playlist.getPlaylistId().equals(selectedPlaylist.getPlaylistId())) {
                        playlistListView.getSelectionModel().select(playlist);
                        break;
                    }
                }
            }
        } catch (SQLException exception) {
            playlistListView.getItems().clear();
            showError("Impossible de charger les playlists depuis la base de donnees.");
        }

        updatePlaylistButtonStates();
    }

    private Optional<String> askPlaylistName(String title, String initialValue) {
        TextInputDialog dialog = new TextInputDialog(initialValue);
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText("Nom");

        Optional<String> result = dialog.showAndWait();
        if (result.isEmpty()) {
            return Optional.empty();
        }

        String name = result.get().trim();
        if (name.isBlank()) {
            showError("Le nom de la playlist est obligatoire.");
            return Optional.empty();
        }

        return Optional.of(name);
    }

    private Playlist getSelectedPlaylistOrShowError() {
        Playlist playlist = playlistListView.getSelectionModel().getSelectedItem();
        if (playlist == null) {
            showError("Selectionne une playlist.");
        }
        return playlist;
    }

    private Song getSelectedSongOrShowError() {
        Song song = tableSongList.getSelectionModel().getSelectedItem();
        if (song == null) {
            showError("Selectionne une chanson.");
        }
        return song;
    }

    private boolean confirm(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
