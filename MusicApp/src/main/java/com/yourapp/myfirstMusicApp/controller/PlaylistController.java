package com.yourapp.myfirstMusicApp.controller;

import java.io.IOException;

import com.yourapp.myfirstMusicApp.MusicAppApplication;
import com.yourapp.myfirstMusicApp.model.Playlist;
import com.yourapp.myfirstMusicApp.model.Song;
import com.yourapp.myfirstMusicApp.repository.PlaylistRepository;

import ch.qos.logback.core.pattern.parser.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class PlaylistController {

    @FXML
    private Label playlistName;

    @FXML
    private Label playlistTotalSong;

    @FXML
    private Label playlistTotalLength;

    @FXML
    private ImageView playlistImg;

    private Playlist playlistData;
  
    private PlaylistDetailController playlistDetailController = MusicAppApplication.getInstance().getPlaylistDetailController();

   

    public void setPlaylistData(Playlist playlist) {
        this.playlistData = playlist;

        // Thiết lập tên playlist
        playlistName.setText(playlist.getName());

        // Thiết lập tổng số bài hát
        playlistTotalSong.setText(playlist.getTotalSong() + " songs");

        // Thiết lập tổng thời lượng
        playlistTotalLength.setText(formatDuration(playlist.getTotalLength()));

      
        }
    

    private String formatDuration(int durationInSeconds) {
        int minutes = durationInSeconds / 60;
        int seconds = durationInSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    @FXML
    private void handlePlaylistClick() {
    MusicAppApplication.getInstance().showPlaylistDetail(playlistData.getId());
    }
    @FXML
    private void deletePlaylist() {
    PlaylistRepository playlistRepository = new PlaylistRepository();
    playlistRepository.delete(playlistData);
    MusicAppApplication.getInstance().getOverviewController().loadPlaylists();
    }
}
