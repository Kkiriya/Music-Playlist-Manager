package com.maisonneuve.music_playlist_manager.controller;

import com.maisonneuve.music_playlist_manager.model.Song;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;

public class PaginationManager {
    private final TableView<Song> tableSongList;
    private final Button btnPrevious;
    private final Button btnNext;
    private final Label lblPage;
    private final ComboBox<String> pageSizeComboBox;
    private List<Song> songs = new ArrayList<>();
    private int currentPage = 1;
    private int pageSize = 25;

    public PaginationManager(
            TableView<Song> tableSongList,
            Button btnPrevious,
            Button btnNext,
            Label lblPage,
            ComboBox<String> pageSizeComboBox
    ) {
        this.tableSongList = tableSongList;
        this.btnPrevious = btnPrevious;
        this.btnNext = btnNext;
        this.lblPage = lblPage;
        this.pageSizeComboBox = pageSizeComboBox;
    }

    /**
     * Connects the pagination buttons and page size choice.
     */
    public void setup() {
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

    public void setSongs(List<Song> songs) {
        this.songs = songs == null ? new ArrayList<>() : new ArrayList<>(songs);
        currentPage = 1;
        updatePage();
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
        int toIndex = Math.min(fromIndex + pageSize, songs.size());

        if (songs.isEmpty()) {
            tableSongList.getItems().clear();
        } else {
            tableSongList.getItems().setAll(songs.subList(fromIndex, toIndex));
        }

        lblPage.setText("Page " + currentPage + " / " + totalPages);
        btnPrevious.setDisable(currentPage <= 1);
        btnNext.setDisable(currentPage >= totalPages);
    }

    private int getTotalPages() {
        if (songs.isEmpty()) {
            return 1;
        }

        // Math.ceil rounds up
        return (int) Math.ceil((double) songs.size() / pageSize);
    }
}
