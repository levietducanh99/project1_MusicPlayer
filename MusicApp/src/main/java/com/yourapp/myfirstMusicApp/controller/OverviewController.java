package com.yourapp.myfirstMusicApp.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.yourapp.myfirstMusicApp.MusicAppApplication;
import com.yourapp.myfirstMusicApp.loader.CustomMenuLoader;
import com.yourapp.myfirstMusicApp.loader.SmallPlayerLoader;
import com.yourapp.myfirstMusicApp.model.Playlist;
import com.yourapp.myfirstMusicApp.model.Song;
import com.yourapp.myfirstMusicApp.repository.FavoritesRepository;
import com.yourapp.myfirstMusicApp.repository.HistoryRepository;
import com.yourapp.myfirstMusicApp.repository.PlaylistRepository;
import com.yourapp.myfirstMusicApp.repository.SongRepository;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OverviewController {
	 private MusicAppApplication app;
	   
	    private HistoryRepository historyRepository;
	    private FavoritesRepository favoritesRepository;
	    private PlaylistRepository playlistRepository;
	    @FXML
	    private VBox customMenuContainer; // Container hiện tại của bạn
	    @FXML
	    private HBox historyContainer; // Container chứa các bài hát
	    @FXML
	    private HBox favouritesContainer; // Container chứa các bài hát
	    @FXML
	    private HBox playlistsContainer; // Container chứa các bài hát
	    @FXML
	    private HBox smallPlayerContainer; // Container hiện tại của bạn
	public OverviewController() {
		 this.historyRepository = new HistoryRepository();
		 this.favoritesRepository  = new FavoritesRepository();
		 this.playlistRepository = new PlaylistRepository();
	}
	  // Thiết lập đối tượng MusicAppApplication cho controller
    public void setApp(MusicAppApplication app) {
        this.app = app;
    }
    public void loadHistory() {
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
                controller.setSongList(songs);
                // Thêm Node vào songContainer
                historyContainer.getChildren().add(songNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void loadFavourites() {
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
                controller.setSongList(songs);
                // Thêm Node vào songContainer
                favouritesContainer.getChildren().add(songNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void loadPlaylists() {
        // Lấy danh sách bài hát từ repository
    	playlistsContainer.getChildren().clear();
        List<Playlist> playlists = playlistRepository.findAll();

        for (Playlist playlist : playlists) {
            try {
                // Tải playlist
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/playlistV1.fxml"));
                Node songNode = loader.load();

                // Lấy SongController từ loader
                PlaylistController controller = loader.getController();

                // Gán dữ liệu cho bài hát
                controller.setPlaylistData(playlist);
               
                // Thêm Node vào songContainer
                playlistsContainer.getChildren().add(songNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void handleCreatePlaylist() {
        // Tạo hộp thoại nhập tên playlist
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Playlist");
        dialog.setHeaderText("Enter the name of the new playlist:");
        dialog.setContentText("Playlist Name:");

        // Lấy tên playlist từ người dùng
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {
            if (!name.trim().isEmpty()) {
                // Tạo đối tượng Playlist mới và lưu vào cơ sở dữ liệu
                Playlist newPlaylist = new Playlist();
                newPlaylist.setName(name);
                playlistRepository.save(newPlaylist);
                
                // Cập nhật giao diện (ví dụ: hiển thị lại danh sách playlist)
                loadPlaylists();  // Gọi phương thức để cập nhật giao diện nếu cần
            }
        });
    }
    @FXML
    public void initialize() {
    	System.out.println("OverviewController initialized.");
    	app = MusicAppApplication.getInstance();
    	   customMenuContainer.getChildren().setAll(CustomMenuLoader.getOverviewMenu(MusicAppApplication.getInstance()));
     	loadHistory();
     	loadFavourites();
     	loadPlaylists();
     	smallPlayerContainer.getChildren().setAll(SmallPlayerLoader.getOverviewSmallPlayer(MusicAppApplication.getInstance()));
    }
    // Cập nhật lại dữ liệu của Overview
    public void updateOverviewData() {
        historyContainer.getChildren().clear();  // Xóa các bài hát cũ
        favouritesContainer.getChildren().clear();  // Xóa các bài hát cũ
        playlistsContainer.getChildren().clear();  // Xóa các playlist cũ

        loadHistory();   // Tải lại danh sách bài hát gần đây
        loadFavourites();   // Tải lại danh sách bài hát yêu thích
        loadPlaylists();   // Tải lại danh sách playlist
    }
}

