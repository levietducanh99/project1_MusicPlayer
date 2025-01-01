package com.yourapp.myfirstMusicApp.controller;

import java.io.IOException;
import java.util.List;

import com.yourapp.myfirstMusicApp.MusicAppApplication;
import com.yourapp.myfirstMusicApp.model.Playlist;
import com.yourapp.myfirstMusicApp.repository.PlaylistRepository;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;

public class PlaylistsController {
	 private MusicAppApplication app;
    @FXML
    private TilePane playlistsContainer;

    private final PlaylistRepository playlistRepository;
	  // Thiết lập đối tượng MusicAppApplication cho controller
public void setApp(MusicAppApplication app) {
   this.app = app;
}
    public PlaylistsController() {
        this.playlistRepository = new PlaylistRepository();
    }

    @FXML
    public void initialize() {
        loadPlaylists();
    }

    private void loadPlaylists() {
        // Lấy danh sách playlist từ repository
        List<Playlist> playlists = playlistRepository.findAll();

        for (Playlist playlist : playlists) {
            try {
                // Tải Playlist.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/playlistV1.fxml"));
                Node playlistNode = loader.load();

                // Lấy PlaylistController từ loader
                PlaylistController controller = loader.getController();

                // Gán dữ liệu playlist vào controller
                controller.setPlaylistData(playlist);

                // Thêm Node vào playlistsContainer
                playlistsContainer.getChildren().add(playlistNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
