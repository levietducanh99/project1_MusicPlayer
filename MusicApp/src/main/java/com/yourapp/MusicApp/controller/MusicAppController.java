package com.yourapp.MusicApp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lombok.Data;
import javafx.scene.control.Slider;
@Data
public class MusicAppController {

    @FXML
    private TextField songSearchField;

    @FXML
    private ListView<String> songListView;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Button playButton;

    // Sample song list for testing
    private String[] songs = {"Song 1", "Song 2", "Song 3", "Song 4", "Song 5"};

    @FXML
    public void initialize() {
        // Initialize the song list view with sample songs
        songListView.getItems().addAll(songs);

        // Optional: Implement a listener for the search field to filter songs
        songSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterSongList(newValue);
        });
    }

    @FXML
    private void handlePlayButton() {
        // Handle play button click (you can later implement actual music playing logic)
        System.out.println("Playing music...");
    }

    private void filterSongList(String query) {
        songListView.getItems().clear();
        for (String song : songs) {
            if (song.toLowerCase().contains(query.toLowerCase())) {
                songListView.getItems().add(song);
            }
        }
    }
}
