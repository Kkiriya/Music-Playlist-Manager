package com.maisonneuve.music_playlist_manager.controller;

import com.maisonneuve.music_playlist_manager.algorithm.Algorithm;
import com.maisonneuve.music_playlist_manager.algorithm.triBulle;
import com.maisonneuve.music_playlist_manager.algorithm.triInsertion;
import com.maisonneuve.music_playlist_manager.algorithm.triMerge;
import com.maisonneuve.music_playlist_manager.algorithm.triRapide;
import com.maisonneuve.music_playlist_manager.algorithm.triSelection;
import com.maisonneuve.music_playlist_manager.model.Song;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class SongSortManager {
    private final ComboBox<String> sortCriterionChoice;
    private final ComboBox<String> sortOrderChoice;
    private final ComboBox<String> sortAlgorithmChoice;
    private final Button sortButton;
    private final Consumer<List<Song>> onSortedSongs;
    private List<Song> songs = new ArrayList<>();

    public SongSortManager(
            ComboBox<String> sortCriterionChoice,
            ComboBox<String> sortOrderChoice,
            ComboBox<String> sortAlgorithmChoice,
            Button sortButton,
            Consumer<List<Song>> onSortedSongs
    ) {
        this.sortCriterionChoice = sortCriterionChoice;
        this.sortOrderChoice = sortOrderChoice;
        this.sortAlgorithmChoice = sortAlgorithmChoice;
        this.sortButton = sortButton;
        this.onSortedSongs = onSortedSongs;
    }

    public void setup() {
        sortButton.setOnAction(event -> sortSongs());
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs == null ? new ArrayList<>() : new ArrayList<>(songs);
    }

    private void sortSongs() {
        Algorithm algorithm = getSelectedAlgorithm();
        Comparator<Song> comparator = getSelectedComparator();

        if ("Decroissant".equals(sortOrderChoice.getValue())) {
            comparator = comparator.reversed();
        }

        algorithm.trier(songs, comparator);
        onSortedSongs.accept(songs);
    }

    private Algorithm getSelectedAlgorithm() {
        String selectedAlgorithm = sortAlgorithmChoice.getValue();

        return switch (selectedAlgorithm) {
            case "Selection sort" -> new triSelection();
            case "Insertion sort" -> new triInsertion();
            case "Merge sort" -> new triMerge();
            case "Quick sort" -> new triRapide();
            default -> new triBulle();
        };
    }

    private Comparator<Song> getSelectedComparator() {
        String selectedCriterion = sortCriterionChoice.getValue();

        return switch (selectedCriterion) {
            case "Artiste" -> Comparator.comparing(Song::getArtist, String.CASE_INSENSITIVE_ORDER);
            case "Duree" -> Comparator.comparingInt(Song::getDuration);
            case "Annee" -> Comparator.comparing(Song::getReleaseYear, String.CASE_INSENSITIVE_ORDER);
            case "Ecoutes" -> Comparator.comparingInt(Song::getListenCount);
            case "Genre" -> Comparator.comparing(song -> song.getGenre().toString(), String.CASE_INSENSITIVE_ORDER);
            default -> Comparator.comparing(Song::getTitle, String.CASE_INSENSITIVE_ORDER);
        };
    }
}
