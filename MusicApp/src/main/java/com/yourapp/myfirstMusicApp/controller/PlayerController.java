package com.yourapp.myfirstMusicApp.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.yourapp.myfirstMusicApp.AudioPlayer;
import com.yourapp.myfirstMusicApp.MusicAppApplication;
import com.yourapp.myfirstMusicApp.loader.CustomMenuLoader;
import com.yourapp.myfirstMusicApp.model.History;
import com.yourapp.myfirstMusicApp.model.Song;
import com.yourapp.myfirstMusicApp.service.PlayerService;
import com.yourapp.myfirstMusicApp.uiComponent.CustomMenu;
import com.yourapp.myfirstMusicApp.uiComponent.NextButton;
import com.yourapp.myfirstMusicApp.uiComponent.PauseButton;

import com.yourapp.myfirstMusicApp.uiComponent.SeekSlider;
import com.yourapp.myfirstMusicApp.uiComponent.VolumeSlider;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Data;
@Data
public class PlayerController {
	// Biến thành singleton
	public static PlayerController instance;

    public PlayerController() {
        // Khởi tạo các thành phần cần thiết
    }

    public static synchronized PlayerController getInstance() {
        if (instance == null) {
            instance = new PlayerController();
        }
        return instance;
    }
    private MusicAppApplication app;
    private AudioPlayer audioPlayer = AudioPlayer.getInstance(); // Get the Singleton instance of AudioPlayer
    private boolean isPlaying = false;  // Trạng thái phát nhạc
    private boolean isPaused = false;   // Trạng thái tạm dừng
    
    private List<Song> currentPlaylist = new ArrayList<>(); // Đảm bảo currentPlaylist không bị null

    private int currentSongIndex = 0;

    private Song currentSong;  // Lưu trữ bài hát hiện tại đang phát
    @FXML
    private VBox customMenuContainer; // Container hiện tại của bạn
   
    @FXML
    private NextButton nextButton;  // Liên kết với nút Next trong FXML
    @FXML
    private PauseButton pauseButton;  // Liên kết với PauseButton trong FXML
    @FXML
    private ImageView albumArtView;
  
    @FXML
    private SeekSlider seekSlider; // Sử dụng SeekSlider
    @FXML
    private VolumeSlider volumeSlider;
    private PlayerService playerService;
    // Cung cấp phương thức để controller nhận tham chiếu của MusicAppApplication
    public void setApp(MusicAppApplication app) {
        this.app = app;
    }
 // Trong PlayerController
    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }
    public void initialize() throws IOException {
    
    	app = MusicAppApplication.getInstance();
        
   // 	  System.out.println("app is:  " + app);
    	// Khởi tạo SeekSlider nếu cần
        if (seekSlider == null) {
            throw new IllegalStateException("SeekSlider is not initialized in FXML.");
            
        }
        if (nextButton == null) {
            System.out.println("NextButton không được khởi tạo từ FXML.");
            return;
        }
        nextButton.bindPlayerController(this);
        customMenuContainer.getChildren().setAll(CustomMenuLoader.getPlayerMenu(MusicAppApplication.getInstance()));
        // Khởi tạo CustomMenu với MusicAppApplication
     // Tạo một instance của controller
       
        
        
     // Khởi tạo CustomMenu và thêm vào mainContainer
 //      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CustomMenu.fxml"));
 //      VBox customMenu = loader.load();
//       CustomMenu controller = loader.getController();
   //    controller.setApp(app); // Gán app trước
     //  System.out.println("app in this is:  " + app);
       
       
        // Chèn CustomMenu vào Pane hiện tại
     //   customMenuContainer1.getChildren().setAll(CustomMenuLoader.getPlayerMenu(MusicAppApplication.getInstance()));
   
             }
    
    
    
    
    // Nhận bài hát từ MusicAppApplication và bắt đầu phát nhạc
    public void setSong(Song song) {
        this.currentSong = song;
    }
    public boolean isPlaying() {
        return isPlaying;
    }
    @FXML
    public void handleGoToLibraryButton() {
    	
        app.showLibraryPage(); // Gọi phương thức chuyển đến trang Library
    }
    @FXML
    public void handlePlaySong1() {
        // Mở cửa sổ chọn file để chọn bài hát
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav", "*.flac"));
        
        // Mở cửa sổ chọn file và lấy file được chọn
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        // Kiểm tra nếu người dùng đã chọn một file
        if (selectedFile != null) {
            // Lấy đường dẫn file đã chọn
            String filePath = selectedFile.getAbsolutePath();
            handlePlaySongFromFile(filePath);

    }
    }
    @FXML
    public void handlePlaySongFromEntity(Song aSong) {
        // Nếu bài hát đã được chọn, thì phát nhạc
    		if ( aSong == null)  showError("No Music Playing", "There is no song");
    		
    		handlePlaySongFromFile(aSong.getFilePath());
    		setupEndOfSongListener();
    }

    // Hàm hiển thị thông báo lỗi nếu gặp sự cố
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handleStop() {
        // Kiểm tra AudioPlayer trước khi dừng
        if (audioPlayer != null && isPlaying) {
            audioPlayer.stop();
            isPlaying = false; // Cập nhật trạng thái
            System.out.println("Playback stopped.");
        } else {
            System.out.println("No music is currently playing.");
            showError("No Music Playing", "There is no music currently playing to stop.");
        }
    }

   

    @FXML
    public void changeSpeed(ActionEvent event) {
        if (audioPlayer != null) {
            // Lấy MenuItem từ sự kiện
            MenuItem selectedMenuItem = (MenuItem) event.getSource();
            String speedText = selectedMenuItem.getText();

            // Chuyển đổi tốc độ từ chuỗi sang số
            double speed = 1.0; // Tốc độ mặc định
            try {
                speed = Double.parseDouble(speedText.replace("x", "")); // Loại bỏ "x" và chuyển đổi thành số
            } catch (NumberFormatException e) {
                System.out.println("Invalid speed format: " + speedText);
            }

            // Thay đổi tốc độ phát
            audioPlayer.speedChange(speed);
            System.out.println("Playback speed changed to " + speedText);
        } else {
            showError("Error", "AudioPlayer is not initialized.");
        }
    }
    public Region getView() {
        // Tạo đối tượng FXMLLoader để tải FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/player.fxml"));
        
        try {
            // Load và trả về layout của FXML
            return loader.load(); // Trả về Region từ FXML đã tải
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error", "Failed to load Player interface.");
            return null; // Trả về null nếu gặp lỗi
        }
    }
    public void handlePlaySongFromFile(String filePath) {
        // Kiểm tra nếu filePath hợp lệ
        if (filePath != null && !filePath.isEmpty()) {
            try {
            	// Nếu có bài hát đang phát, reset trạng thái PauseButton
       //         if (pauseButton != null) {
     //               pauseButton.resetPauseButton();  // Đặt lại trạng thái nút pause
           //     }
                // Khởi tạo AudioPlayer và phát nhạc từ file
                
                audioPlayer = AudioPlayer.getInstance();  // Use Singleton instance
                setupEndOfSongListener();
                audioPlayer.play(filePath); // Dùng phương thức play từ AudioPlayer
                
             // Hiển thị ảnh bìa bài hát
                Image albumArt = audioPlayer.extractAlbumArt(); // Gọi phương thức từ AudioPlayer
                if (albumArt != null) {
                    albumArtView.setImage(albumArt); // albumArtView là một ImageView
                } else {
                    albumArtView.setImage(new Image(getClass().getResourceAsStream("/pic/gau.jpg"))); // Xóa ảnh nếu không tìm thấy
                }
         //       seekSlider.reset(); // Reset slider trước
               
          //      seekSlider.activate(); // Kích hoạt slider sau khi bài hát bắt đầu
             // Liên kết SeekSlider với AudioPlayer
      //          seekSlider.bindAudioPlayer(audioPlayer);
             // Liên kết PauseButton với AudioPlayer
       //         pauseButton.bindAudioPlayer(audioPlayer);
                isPlaying = true; // Đặt trạng thái phát nhạc là true
                System.out.println("Đang phát bài hát: " + filePath);
             // Lưu lịch sử nghe nhạc vào cơ sở dữ liệu
                History.savePlayHistory(filePath); // Gọi phương thức tĩnh từ class History
            } catch (Exception e) {
                e.printStackTrace();
                showError("Error", "Unable to play the selected music file.");
            }
        } else { 
            showError("Invalid Song", "The song file path is invalid.");
        }
    }
   


 
    public void playNextSong() {
    	audioPlayer.stop();
    	  currentSongIndex = (currentSongIndex + 1) % currentPlaylist.size(); // Phát lặp vòng nếu hết danh sách
        if (currentSongIndex < currentPlaylist.size()) {
            handlePlaySongFromEntity(currentPlaylist.get(currentSongIndex));
        } else {
            System.out.println("Đã phát hết danh sách bài hát.");
        }
    }

    private void setupEndOfSongListener() {
        // Giả sử AudioPlayer có một listener để phát hiện bài hát kết thúc
    	
        audioPlayer.setOnEndOfMedia(() -> {
            playNextSong();
            
        });
    }
    public void playAllSongs(List<Song> songLibrary, Song selectedSong) {
     //   System.out.println("Danh sách bài hát hiện tại (trước khi cập nhật): " + currentPlaylist);
      //  System.out.println("Danh sách bài hát mới: " + songLibrary);

        // Cập nhật danh sách bài hát
        this.currentPlaylist.clear();
        this.currentPlaylist.addAll(songLibrary);

        // Xác định chỉ mục của bài hát được chọn
        currentSongIndex = currentPlaylist.indexOf(selectedSong);
        System.out.println("Chỉ mục bài hát được chọn: " + currentSongIndex);

        // Phát bài hát được chọn nếu tìm thấy
        if (currentSongIndex != -1) {
            handlePlaySongFromEntity(currentPlaylist.get(currentSongIndex));
            setupEndOfSongListener(); // Thiết lập sự kiện khi bài hát kết thúc
        } else {
            System.out.println("Không tìm thấy bài hát được chọn trong danh sách.");
        }
    }



}
