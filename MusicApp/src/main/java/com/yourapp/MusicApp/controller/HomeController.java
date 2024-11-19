package com.yourapp.MusicApp.controller;

import java.awt.event.ActionEvent;
import java.io.File;

import com.yourapp.MusicApp.AudioPlayer;
import com.yourapp.MusicApp.MusicAppApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class HomeController {

    private MusicAppApplication app;
    private AudioPlayer audioPlayer = new AudioPlayer();  // Khởi tạo AudioPlayer

    // Cung cấp phương thức để controller nhận tham chiếu của MusicAppApplication
    public void setApp(MusicAppApplication app) {
        this.app = app;
    }

    @FXML
    public void handleGoToLibraryButton() {
        app.showLibraryPage();  // Gọi phương thức chuyển đến trang Library
    }
    @FXML
    public void handlePlaySong() {
        // Mở cửa sổ chọn file để chọn bài hát
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav", "*.flac"));
        
        // Mở cửa sổ chọn file và lấy file được chọn
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        // Kiểm tra nếu người dùng đã chọn một file
        if (selectedFile != null) {
            // Lấy đường dẫn file đã chọn
            String filePath = selectedFile.getAbsolutePath();
            
            // Cố gắng phát bài hát
            try {
                audioPlayer.play(filePath);  // Sử dụng phương thức play từ AudioPlayer để phát nhạc
                System.out.println("Đang phát bài hát: " + selectedFile.getName());
            } catch (Exception e) {
                // In lỗi nếu không thể phát bài hát
                e.printStackTrace();
                showError("Error", "Unable to play the selected music file.");
            }
        } else {
            // Thông báo nếu không có file nào được chọn
            showError("No File Selected", "Please select a valid audio file.");
        }
    }

    // Hàm hiển thị thông báo lỗi nếu gặp sự cố
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
