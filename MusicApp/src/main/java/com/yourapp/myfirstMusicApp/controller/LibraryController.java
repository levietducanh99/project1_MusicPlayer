package com.yourapp.myfirstMusicApp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import com.yourapp.myfirstMusicApp.MusicAppApplication;
import com.yourapp.myfirstMusicApp.SongManager;
import com.yourapp.myfirstMusicApp.loader.CustomMenuLoader;
import com.yourapp.myfirstMusicApp.loader.SmallPlayerLoader;
import com.yourapp.myfirstMusicApp.model.Song;
import com.yourapp.myfirstMusicApp.repository.SongRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class LibraryController {

    private MusicAppApplication app;  
    private SongRepository songRepository;
    @FXML
    private VBox customMenuContainer; // Container hiện tại của bạn
    @FXML
    private HBox smallPlayerContainer; // Container hiện tại của bạn
    @FXML
    private VBox songContainer; // Container chứa các bài hát
    @FXML
    private TextField searchTextField; // TextField để nhập từ khóa tìm kiếm
    // Constructor to inject SongRepository
    public LibraryController() {
        this.songRepository = new SongRepository(); // Assuming SongRepository is properly initialized
    }

    
    @FXML
    private void handleGoBackToHome(ActionEvent event) {
        app.showPlayerPage(); // Quay lại trang Home
    }

    // Thiết lập đối tượng MusicAppApplication cho controller
    public void setApp(MusicAppApplication app) {
        this.app = app;
    }

    @FXML
   
    public void handleAddSong() {
        // Mở cửa sổ chọn thư mục
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(new Stage());

        if (selectedDirectory != null) {
            // Lấy tất cả các tệp âm thanh trong thư mục
            File[] audioFiles = selectedDirectory.listFiles((dir, name) -> 
                name.endsWith(".mp3") || name.endsWith(".wav") || name.endsWith(".flac"));

            if (audioFiles != null) {
                for (File audioFile : audioFiles) {
                    try {
                        // Đọc metadata từ file âm thanh
                        AudioFile file = AudioFileIO.read(audioFile);
                        Tag tag = file.getTag();

                        String title = tag.getFirst(FieldKey.TITLE);  // Tên bài hát
                        String artist = tag.getFirst(FieldKey.ARTIST); // Tên ca sĩ
                        long duration = file.getAudioHeader().getTrackLength(); // Độ dài bài hát (tính bằng giây)
                        
                        // Thêm bài hát vào cơ sở dữ liệu
                        Song song = new Song();
                        song.setTitle(title);
                        song.setArtist(artist);
                        song.setFilePath(audioFile.getAbsolutePath());
                        song.setDuration(duration);

                        // Lưu bài hát vào database thông qua SongRepository
                        songRepository.save(song);

                        System.out.println("Bài hát đã được thêm: " + title + " - " + artist);
                    } catch (Exception e) {
                        e.printStackTrace();
                        // Xử lý lỗi cho từng bài hát
                        showErrorDialog("Lỗi khi thêm bài hát", e.getMessage());
                    }
                }

                // Cập nhật thư viện sau khi thêm bài hát
               loadSongs();
            }
        }
    }


    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private ListView<Song> songListView;
   
    @FXML
    public void initialize() {
    	app = MusicAppApplication.getInstance();
        // Gọi phương thức showAllSongs() để hiển thị danh sách bài hát từ cơ sở dữ liệu
    //    showAllSongs();
    //    songListView.setOnMouseClicked(this::handleSongDoubleClick);
        customMenuContainer.getChildren().setAll(CustomMenuLoader.getLibraryMenu(MusicAppApplication.getInstance()));
     //   customMenuContainer.getChildren().setAll(new CustomMenu(MusicAppApplication.getInstance()));
        smallPlayerContainer.getChildren().setAll(SmallPlayerLoader.getLibrarySmallPlayer(MusicAppApplication.getInstance()));
        loadSongs();
    }

    public void loadSongs() {
        // Lấy danh sách bài hát từ repository
        List<Song> songs = SongRepository.findAll();
        songContainer.getChildren().clear(); // Xóa các bài hát cũ
        for (Song song : songs) {
            try {
                // Tải Song.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Song.fxml"));
                Node songNode = loader.load();

                // Lấy SongController từ loader
                SongController controller = loader.getController();

                // Gán dữ liệu cho bài hát
                controller.setSongData(song);
                controller.setSongList(songs);
                // Thêm Node vào songContainer
                songContainer.getChildren().add(songNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void refreshLibrary() {
        // Cập nhật danh sách bài hát trong giao diện, giả sử bạn dùng ListView
    	
    }
    public void handleSearch(ActionEvent event) {
        String searchQuery = searchTextField.getText().toLowerCase(); // Lấy từ khóa tìm kiếm và chuyển thành chữ thường
        List<Song> allSongs = SongRepository.findAll(); // Lấy tất cả bài hát từ repository

        // Lọc bài hát theo tên hoặc tác giả
        List<Song> filteredSongs = allSongs.stream()
                .filter(song -> song.getTitle().toLowerCase().contains(searchQuery) || song.getArtist().toLowerCase().contains(searchQuery))
                .collect(Collectors.toList());

        // Cập nhật lại danh sách bài hát trong giao diện
        updateSongList(filteredSongs);
    }
    public void updateSongList(List<Song> songs) {
        songContainer.getChildren().clear(); // Xóa các bài hát cũ
        for (Song song : songs) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Song.fxml"));
                Node songNode = loader.load();

                SongController controller = loader.getController();
                controller.setSongData(song);
                controller.setSongList(songs);
                songContainer.getChildren().add(songNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}