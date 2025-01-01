package com.yourapp.myfirstMusicApp.controller;

import java.awt.TextField;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.yourapp.myfirstMusicApp.MusicAppApplication;
import com.yourapp.myfirstMusicApp.loader.CustomMenuLoader;
import com.yourapp.myfirstMusicApp.loader.SmallPlayerLoader;
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
	    @FXML
	    private HBox smallPlayerContainer; // Container hiện tại của bạn
	    @FXML
	    private javafx.scene.control.TextField searchTextField; // TextField để nhập từ khóa tìm kiếm
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
          // Truyền danh sách bài hát yêu thích vào controller
             controller.setSongList(songs);
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
  	smallPlayerContainer.getChildren().setAll(SmallPlayerLoader.getFavouriteSmalPlayer(MusicAppApplication.getInstance()));
 }
 // Cập nhật lại dữ liệu của Favourites
 public void updateFavouritesData() {
     favouritesContainer.getChildren().clear(); // Xóa các bài hát cũ
     loadFavourites(); // Tải lại các bài hát yêu thích
 }
 @FXML
 public void handleSearch() {
     String searchQuery = searchTextField.getText().toLowerCase(); // Lấy từ khóa tìm kiếm và chuyển thành chữ thường
     List<Song> allSongs = favoritesRepository.findAllFavouriteSongs(); // Lấy tất cả bài hát yêu thích từ repository

     // Lọc bài hát theo tên hoặc tác giả
     List<Song> filteredSongs = allSongs.stream()
             .filter(song -> song.getTitle().toLowerCase().contains(searchQuery) || song.getArtist().toLowerCase().contains(searchQuery))
             .collect(Collectors.toList());

     // Cập nhật lại danh sách bài hát trong giao diện
     updateFavouritesList(filteredSongs);
 }

 // Cập nhật lại danh sách bài hát yêu thích
 private void updateFavouritesList(List<Song> songs) {
     favouritesContainer.getChildren().clear(); // Xóa các bài hát cũ
     for (Song song : songs) {
         try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/songV2.fxml"));
             Node songNode = loader.load();

             SongControllerV2 controller = loader.getController();
             controller.setSongData(song);
             controller.setSongList(songs);
             favouritesContainer.getChildren().add(songNode);
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
 }
}
