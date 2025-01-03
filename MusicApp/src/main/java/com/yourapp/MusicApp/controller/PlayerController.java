package com.yourapp.MusicApp.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.yourapp.MusicApp.AudioPlayer;
import com.yourapp.MusicApp.MusicAppApplication;
import com.yourapp.MusicApp.model.Song;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Data;
@Data
public class PlayerController {

    private MusicAppApplication app;
    private AudioPlayer audioPlayer;  // Không khởi tạo sẵn AudioPlayer
    private boolean isPlaying = false;  // Trạng thái phát nhạc
    private boolean isPaused = false;   // Trạng thái tạm dừng
    private List<Song> currentPlaylist = new ArrayList<>();
    private int currentSongIndex = 0;

    private Song currentSong;  // Lưu trữ bài hát hiện tại đang phát
    @FXML
    private ImageView albumArtView;
    @FXML
    private Button pauseButton;  // Liên kết với nút pause trong FXML
    @FXML
    private Slider seekSlider;
    
    // Cung cấp phương thức để controller nhận tham chiếu của MusicAppApplication
    public void setApp(MusicAppApplication app) {
        this.app = app;
    }

    public void initialize() {
        // Liên kết Slider với trạng thái tiến trình của bài hát
        if (audioPlayer != null) {
            audioPlayer.setOnProgressListener(currentTime -> {
                if (!seekSlider.isValueChanging()) { // Chỉ cập nhật nếu người dùng không kéo
                    double progress = currentTime / audioPlayer.getTotalDuration();
                    seekSlider.setValue(progress * 100); // Đặt giá trị cho Slider (0 - 100)
                }
            });

            // Cài đặt tối đa cho Slider
            seekSlider.setMax(100);
        }
     // Lắng nghe khi giá trị của Slider thay đổi do người dùng kéo
        seekSlider.setOnMouseReleased(event -> handleSeek());

        // Nếu cần cập nhật khi giá trị Slider đang thay đổi (khi kéo), dùng sự kiện này
        seekSlider.valueChangingProperty().addListener((observable, wasChanging, isChanging) -> {
            if (!isChanging) {
                handleSeek(); // Gọi lại handleSeek khi người dùng thả chuột
            }
        });
     // Khi người dùng bấm vào slider, chúng ta sẽ tua đến vị trí đó
        seekSlider.setOnMouseClicked(event -> handleSeekByClick(event));
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
    public void handlePause() {
        // Gọi phương thức pause từ AudioPlayer
      if(audioPlayer != null)  audioPlayer.pause();
      else System.out.print("no media played");

        // Kiểm tra trạng thái phát nhạc và thay đổi hình ảnh của nút
        if (isPaused) {
        	pauseButton.getStyleClass().remove("paused");
        } else {
        	pauseButton.getStyleClass().add("paused");
        }

        // Lật trạng thái paused
        isPaused = !isPaused;
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
                // Khởi tạo AudioPlayer và phát nhạc từ file
                audioPlayer = new AudioPlayer();
                audioPlayer.play(filePath); // Dùng phương thức play từ AudioPlayer
                
             // Hiển thị ảnh bìa bài hát
                Image albumArt = audioPlayer.extractAlbumArt(); // Gọi phương thức từ AudioPlayer
                if (albumArt != null) {
                    albumArtView.setImage(albumArt); // albumArtView là một ImageView
                } else {
                    albumArtView.setImage(null); // Xóa ảnh nếu không tìm thấy
                }
                
                
             // Liên kết Slider với bài hát
                audioPlayer.setOnProgressListener(currentTime -> {
                    if (!seekSlider.isValueChanging()) {
                        double progress = currentTime / audioPlayer.getTotalDuration();
                        seekSlider.setValue(progress * 100);
                    }
                });

                seekSlider.setMax(100); // Đặt giá trị tối đa cho Slider
                isPlaying = true; // Đặt trạng thái phát nhạc là true
                System.out.println("Đang phát bài hát: " + filePath);
            } catch (Exception e) {
                e.printStackTrace();
                showError("Error", "Unable to play the selected music file.");
            }
        } else { 
            showError("Invalid Song", "The song file path is invalid.");
        }
    }
   


    @FXML
    private void handleSeek() {
        if (audioPlayer != null) {
            // Tính thời gian cần tua tới
            double seekTime = seekSlider.getValue() / 100 * audioPlayer.getTotalDuration();
            audioPlayer.seek(seekTime); // Tua đến thời gian tương ứng
            
        }
    }
    private void handleSeekByClick(MouseEvent event) {
        if (audioPlayer != null) {
            // Lấy vị trí bấm chuột trên slider (vị trí này sẽ là 0 đến 100%)
            double clickPosition = event.getX() / seekSlider.getWidth(); // Tỷ lệ phần trăm vị trí bấm

            // Lấy tổng thời gian bài hát
            double totalDuration = audioPlayer.getTotalDuration();

            // Tính toán vị trí seek theo thời gian
            double seekTime = clickPosition * totalDuration;

            // Gọi phương thức seek để tua bài hát đến vị trí mới
            audioPlayer.seek(seekTime);

            
        } else {
            System.out.println("AudioPlayer is not initialized or no song is playing.");
        }
    }
    private void playNextSong() {
        currentSongIndex++;
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
        // Lưu danh sách bài hát hiện tại
        currentPlaylist = songLibrary;

        // Xác định chỉ mục của bài hát được chọn
        currentSongIndex = currentPlaylist.indexOf(selectedSong);

        // Phát bài hát được chọn
        if (currentSongIndex != -1) {
            handlePlaySongFromEntity(currentPlaylist.get(currentSongIndex));
            setupEndOfSongListener();
        }
    }

}
