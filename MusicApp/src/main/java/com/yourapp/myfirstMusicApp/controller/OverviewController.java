package com.yourapp.myfirstMusicApp.controller;

import java.io.IOException;
import java.util.List;

import com.yourapp.myfirstMusicApp.MusicAppApplication;
import com.yourapp.myfirstMusicApp.loader.CustomMenuLoader;
import com.yourapp.myfirstMusicApp.loader.SmallPlayerLoader;
import com.yourapp.myfirstMusicApp.model.Song;
import com.yourapp.myfirstMusicApp.repository.FavoritesRepository;
import com.yourapp.myfirstMusicApp.repository.HistoryRepository;
import com.yourapp.myfirstMusicApp.repository.SongRepository;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OverviewController {
	 private MusicAppApplication app;
	   
	    private HistoryRepository historyRepository;
	    private FavoritesRepository favoritesRepository;
	    @FXML
	    private VBox customMenuContainer; // Container hiện tại của bạn
	    @FXML
	    private HBox historyContainer; // Container chứa các bài hát
	    @FXML
	    private HBox favouritesContainer; // Container chứa các bài hát
	public OverviewController() {
		 this.historyRepository = new HistoryRepository();
		 this.favoritesRepository  = new FavoritesRepository();
	}
	  // Thiết lập đối tượng MusicAppApplication cho controller
    public void setApp(MusicAppApplication app) {
        this.app = app;
    }
    private void loadHistory() {
        // Lấy danh sách bài hát từ repository
        List<Song> songs = historyRepository.findRecentSongs();

        for (Song song : songs) {
            try {
                // Tải Song.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/songV2.fxml"));
                Node songNode = loader.load();

                // Lấy SongController từ loader
                SongControllerV2 controller = loader.getController();

                // Gán dữ liệu cho bài hát
                controller.setSongData(song);

                // Thêm Node vào songContainer
                historyContainer.getChildren().add(songNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void loadFavourites() {
        // Lấy danh sách bài hát từ repository
        List<Song> songs = favoritesRepository.findRecentFavouriteSongs();

        for (Song song : songs) {
            try {
                // Tải Song.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/songV2.fxml"));
                Node songNode = loader.load();

                // Lấy SongController từ loader
                SongControllerV2 controller = loader.getController();

                // Gán dữ liệu cho bài hát
                controller.setSongData(song);

                // Thêm Node vào songContainer
                favouritesContainer.getChildren().add(songNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void initialize() {
    	System.out.println("OverviewController initialized.");
    	app = MusicAppApplication.getInstance();
    	   customMenuContainer.getChildren().setAll(CustomMenuLoader.getOverviewMenu(MusicAppApplication.getInstance()));
     	loadHistory();
     	loadFavourites();
    }
}

