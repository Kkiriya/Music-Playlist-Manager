package com.maisonneuve.music_playlist_manager.controller;

import com.maisonneuve.music_playlist_manager.model.Song;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SongFilterManager {
    private static final String ALL_ARTISTS = "Tous";
    private static final String ALL_DECADES = "Toutes";
    private static final String ALL_GENRES = "Tous";

    private final TextField searchField;
    private final ComboBox<String> genreFilter;
    private final ComboBox<String> decennieFilter;
    private final ComboBox<String> artistFilter;
    private final Slider maxDurationSlider;
    private final Slider minListenCountSlider;
    private final Button resetFiltersButton;
    private final Consumer<List<Song>> onFilteredSongs;
    private List<Song> allSongs = new ArrayList<>();

    public SongFilterManager(
            TextField searchField,
            ComboBox<String> genreFilter,
            ComboBox<String> decennieFilter,
            ComboBox<String> artistFilter,
            Slider maxDurationSlider,
            Slider minListenCountSlider,
            Button resetFiltersButton,
            Consumer<List<Song>> onFilteredSongs
    ) {
        this.searchField = searchField;
        this.genreFilter = genreFilter;
        this.decennieFilter = decennieFilter;
        this.artistFilter = artistFilter;
        this.maxDurationSlider = maxDurationSlider;
        this.minListenCountSlider = minListenCountSlider;
        this.resetFiltersButton = resetFiltersButton;
        this.onFilteredSongs = onFilteredSongs;
    }

    public void setup() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> applyFilters());
        genreFilter.valueProperty().addListener((observable, oldValue, newValue) -> applyFilters());
        decennieFilter.valueProperty().addListener((observable, oldValue, newValue) -> applyFilters());
        artistFilter.valueProperty().addListener((observable, oldValue, newValue) -> applyFilters());
        maxDurationSlider.valueProperty().addListener((observable, oldValue, newValue) -> applyFilters());
        minListenCountSlider.valueProperty().addListener((observable, oldValue, newValue) -> applyFilters());
        resetFiltersButton.setOnAction(event -> resetFilters());
    }

    public void setSongs(List<Song> songs) {
        allSongs = songs == null ? new ArrayList<>() : new ArrayList<>(songs);
        setupArtistChoices();
        applyFilters();
    }

    private void setupArtistChoices() {
        List<String> artists = new ArrayList<>();
        artists.add(ALL_ARTISTS);

        for (Song song : allSongs) {
            if (!artists.contains(song.getArtist())) {
                artists.add(song.getArtist());
            }
        }

        artistFilter.getItems().setAll(artists);
        artistFilter.setValue(ALL_ARTISTS);
    }

    private void applyFilters() {
        List<Song> filteredSongs = new ArrayList<>();

        for (Song song : allSongs) {
            if (matchesSearch(song)
                    && matchesGenre(song)
                    && matchesDecade(song)
                    && matchesArtist(song)
                    && matchesDuration(song)
                    && matchesListenCount(song)) {
                filteredSongs.add(song);
            }
        }

        onFilteredSongs.accept(filteredSongs);
    }

    private boolean matchesSearch(Song song) {
        String searchText = searchField.getText();
        if (searchText == null || searchText.isBlank()) {
            return true;
        }

        String search = searchText.toLowerCase();
        return song.getTitle().toLowerCase().contains(search)
                || song.getArtist().toLowerCase().contains(search)
                || song.getAlbum().toLowerCase().contains(search);
    }

    private boolean matchesGenre(Song song) {
        String selectedGenre = genreFilter.getValue();
        return selectedGenre == null || ALL_GENRES.equals(selectedGenre) || song.getGenre().toString().equals(selectedGenre);
    }

    private boolean matchesDecade(Song song) {
        String selectedDecade = decennieFilter.getValue();
        if (selectedDecade == null || ALL_DECADES.equals(selectedDecade)) {
            return true;
        }

        int releaseYear = Integer.parseInt(song.getReleaseDate().substring(0, 4));
        int decadeStart = Integer.parseInt(selectedDecade.substring(0, 4));
        return releaseYear >= decadeStart && releaseYear <= decadeStart + 9;
    }

    private boolean matchesArtist(Song song) {
        String selectedArtist = artistFilter.getValue();
        return selectedArtist == null || ALL_ARTISTS.equals(selectedArtist) || song.getArtist().equals(selectedArtist);
    }

    private boolean matchesDuration(Song song) {
        return song.getDuration() <= (int) maxDurationSlider.getValue();
    }

    private boolean matchesListenCount(Song song) {
        return song.getListenCount() >= (int) minListenCountSlider.getValue();
    }

    private void resetFilters() {
        searchField.clear();
        genreFilter.setValue(ALL_GENRES);
        decennieFilter.setValue(ALL_DECADES);
        artistFilter.setValue(ALL_ARTISTS);
        maxDurationSlider.setValue(maxDurationSlider.getMax());
        minListenCountSlider.setValue(minListenCountSlider.getMin());
        applyFilters();
    }
}
