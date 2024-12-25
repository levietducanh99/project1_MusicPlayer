package com.yourapp.myfirstMusicApp.controller;

import java.io.IOException;
import java.util.List;

import com.yourapp.myfirstMusicApp.MusicAppApplication;
import com.yourapp.myfirstMusicApp.loader.CustomMenuLoader;
import com.yourapp.myfirstMusicApp.model.Song;
import com.yourapp.myfirstMusicApp.repository.FavoritesRepository;
import com.yourapp.myfirstMusicApp.repository.HistoryRepository;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class FavouritesController {

	 private MusicAppApplication app;
	
	   
	    private FavoritesRepository favoritesRepository;
	   
	    @FXML
	    private VBox customMenuContainer; // Container hiện tại của bạn
	    @FXML
	    private TilePane favouritesContainer; // Container chứa các bài hát
	public FavouritesController() {
		 
		 this.favoritesRepository  = new FavoritesRepository();
	}
	  // Thiết lập đối tượng MusicAppApplication cho controller
 public void setApp(MusicAppApplication app) {
     this.app = app;
 }
 
 
 private void loadFavourites() {
     // Lấy danh sách bài hát từ repository
     List<Song> songs = favoritesRepository.findAllFavouriteSongs();

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
 	
 	app = MusicAppApplication.getInstance();
    customMenuContainer.getChildren().setAll(CustomMenuLoader.getFavouritesMenu(MusicAppApplication.getInstance()));
  	loadFavourites();
 }
}
