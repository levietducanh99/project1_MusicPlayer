package com.yourapp.myfirstMusicApp.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import com.yourapp.myfirstMusicApp.MusicAppApplication;
import com.yourapp.myfirstMusicApp.SongManager;
import com.yourapp.myfirstMusicApp.loader.CustomMenuLoader;
import com.yourapp.myfirstMusicApp.loader.SmallPlayerLoader;
import com.yourapp.myfirstMusicApp.model.Song;
import com.yourapp.myfirstMusicApp.repository.HistoryRepository;
import com.yourapp.myfirstMusicApp.repository.SongRepository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class HistoryController {

	 private MusicAppApplication app;  
	    private HistoryRepository historyRepository;
	    @FXML
	    private VBox customMenuContainer; // Container hiện tại của bạn
	    @FXML
	    private HBox smallPlayerContainer; // Container hiện tại của bạn
	    @FXML
	    private VBox songContainer; // Container chứa các bài hát
	   
	    // Constructor to inject SongRepository
	    public HistoryController() {
	        this.historyRepository = new HistoryRepository(); // Assuming SongRepository is properly initialized
	    }

	    
	    @FXML
	    private void handleGoBackToHome(ActionEvent event) {
	        app.showPlayerPage(); // Quay lại trang Home
	    }

	    // Thiết lập đối tượng MusicAppApplication cho controller
	    public void setApp(MusicAppApplication app) {
	        this.app = app;
	    }

	  

	    private void showErrorDialog(String title, String message) {
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle(title);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }
	   
	   
	    @FXML
	    public void initialize() {
	    	app = MusicAppApplication.getInstance();
	        // Gọi phương thức showAllSongs() để hiển thị danh sách bài hát từ cơ sở dữ liệu
	    //    showAllSongs();
	    //    songListView.setOnMouseClicked(this::handleSongDoubleClick);
	        customMenuContainer.getChildren().setAll(CustomMenuLoader.getHistoryMenu(MusicAppApplication.getInstance()));
	     //   customMenuContainer.getChildren().setAll(new CustomMenu(MusicAppApplication.getInstance()));
	//        smallPlayerContainer.getChildren().setAll(SmallPlayerLoader.getLibrarySmallPlayer(MusicAppApplication.getInstance()));
	        loadSongs();
	    }

	    private void loadSongs() {
	        // Lấy danh sách bài hát từ repository
	        List<Song> songs = historyRepository.findAllSong();

	        for (Song song : songs) {
	            try {
	                // Tải Song.fxml
	                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Song.fxml"));
	                Node songNode = loader.load();

	                // Lấy SongController từ loader
	                SongController controller = loader.getController();

	                // Gán dữ liệu cho bài hát
	                controller.setSongData(song);

	                // Thêm Node vào songContainer
	                songContainer.getChildren().add(songNode);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    
	    private void handleSongSelection(Song selectedSong) {
	        // Lấy danh sách bài hát từ ListView
	      
	        // Truyền danh sách bài hát và bài hát được chọn vào SongManager
	        SongManager.getInstance().setSongList(SongRepository.findAll());
	        SongManager.getInstance().setSelectedSong(selectedSong);

	    
	    }
	}
