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

public class LibraryController {

    private MusicAppApplication app;
    private List<Song> songLibrary;
    private SongRepository songRepository;
    @FXML
    private VBox customMenuContainer; // Container hiện tại của bạn
    @FXML
    private HBox smallPlayerContainer; // Container hiện tại của bạn
    @FXML
    private VBox songContainer; // Container chứa các bài hát
   
    // Constructor to inject SongRepository
    public LibraryController() {
        this.songRepository = new SongRepository(); // Assuming SongRepository is properly initialized
    }
    public void initializeLibrary() {
        // Lấy tất cả bài hát từ cơ sở dữ liệu và lưu vào songLibrary
        songLibrary = songRepository.findAll();
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
        // Mở cửa sổ chọn file
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav", "*.flac"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            try {
                // Đọc metadata từ file
                AudioFile audioFile = AudioFileIO.read(selectedFile);
                Tag tag = audioFile.getTag();

                String title = tag.getFirst(FieldKey.TITLE);  // Tên bài hát
                String artist = tag.getFirst(FieldKey.ARTIST); // Tên ca sĩ
                long duration = audioFile.getAudioHeader().getTrackLength(); // Độ dài bài hát (tính bằng giây)
                
                // Thêm bài hát vào cơ sở dữ liệu (chỉ cần đường dẫn file)
                Song song = new Song();
                song.setTitle(title);
                song.setArtist(artist);
                song.setFilePath(selectedFile.getAbsolutePath());
                song.setDuration(duration);

                // Lưu bài hát vào database thông qua SongRepository
                songRepository.save(song);

                // Hiển thị thông báo hoặc xử lý gì đó sau khi thêm thành công
                System.out.println("Bài hát đã được thêm: " + title + " - " + artist);
                refreshLibrary();

            } catch (Exception e) {
                e.printStackTrace();
                // Xử lý lỗi, ví dụ thông báo lỗi cho người dùng
                showErrorDialog("Lỗi khi thêm bài hát", e.getMessage());
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
    private void showAllSongs() {
    	
        try {
            // Lấy danh sách bài hát từ cơ sở dữ liệu
            ObservableList<Song> songList = FXCollections.observableArrayList(songRepository.findAll());

            // Gán danh sách bài hát vào ListView
            songListView.setItems(songList);

            // Thiết lập cách hiển thị (tên bài hát và ca sĩ) trong ListView
            songListView.setCellFactory(listView -> new javafx.scene.control.ListCell<Song>() {
                @Override
                protected void updateItem(Song song, boolean empty) {
                    super.updateItem(song, empty);
                    if (empty || song == null) {
                        setText(null);
                    } else {
                        setText(song.getTitle() + " - " + song.getArtist());
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            showErrorDialog("Lỗi khi tải danh sách bài hát", e.getMessage());
        }
    }
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
    public void handleSongDoubleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Song selectedSong = songListView.getSelectionModel().getSelectedItem();
            if (selectedSong != null) {
                app.showPlayerAndPlay(selectedSong);  // Chuyển đến trang Player và phát bài hát
            }
        }
    }
    private void loadSongs() {
        // Lấy danh sách bài hát từ repository
        List<Song> songs = SongRepository.findAll();

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
    public void refreshLibrary() {
        // Cập nhật danh sách bài hát trong giao diện, giả sử bạn dùng ListView
    	showAllSongs();
    }
    
    private void handleSongSelection(Song selectedSong) {
        // Lấy danh sách bài hát từ ListView
      
        // Truyền danh sách bài hát và bài hát được chọn vào SongManager
        SongManager.getInstance().setSongList(SongRepository.findAll());
        SongManager.getInstance().setSelectedSong(selectedSong);

    
    }
}