package com.yourapp.myfirstMusicApp.controller;

import java.io.IOException;
import java.util.List;

import com.yourapp.myfirstMusicApp.MusicAppApplication;
import com.yourapp.myfirstMusicApp.model.Song;
import com.yourapp.myfirstMusicApp.repository.SongPlaylistRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;

public class PlaylistDetailController {
	private MusicAppApplication app;

    @FXML
    private TilePane songsContainer;

    private final SongPlaylistRepository songPlaylistRepository;

    public PlaylistDetailController() {
        this.songPlaylistRepository = new SongPlaylistRepository();
    }

	  // Thiết lập đối tượng MusicAppApplication cho controller
    public void setApp(MusicAppApplication app) {
        this.app = app;
    }
    public void loadSongsByPlaylistId(Long playlistId) {
        songsContainer.getChildren().clear(); // Xóa danh sách cũ

        // Lấy danh sách bài hát từ repository
        List<Song> songs = songPlaylistRepository.findSongsByPlaylistId(playlistId);

        for (Song song : songs) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/songV3.fxml"));
                Node songNode = loader.load();

                // Gán dữ liệu bài hát vào controller
                SongControllerV3 controller = loader.getController();
                controller.setSongData(song);
                controller.setSongList(songs);
                controller.setCurrentPlaylistId(playlistId);
                songsContainer.getChildren().add(songNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    void backToOverview()
    {
    	MusicAppApplication.getInstance().showOverviewPage();
    	
    }
}
