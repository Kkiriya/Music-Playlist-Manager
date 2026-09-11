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
    }
}
