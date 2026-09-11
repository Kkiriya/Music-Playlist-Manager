package com.maisonneuve.music_playlist_manager.controller;

import com.maisonneuve.music_playlist_manager.model.Genre;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class PrincipalController {
    @FXML
    private ComboBox<Genre> genreFilter;

    @FXML
    public void initialize() {
        genreFilter.getItems().setAll(Genre.values());
    }
}
