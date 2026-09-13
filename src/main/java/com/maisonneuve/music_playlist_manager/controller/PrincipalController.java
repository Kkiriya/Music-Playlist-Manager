package com.maisonneuve.music_playlist_manager.controller;

import com.maisonneuve.music_playlist_manager.model.Genre;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

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
    public void initialize() {
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
}
