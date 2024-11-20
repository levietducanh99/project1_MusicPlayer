package com.yourapp.MusicApp;

import com.yourapp.MusicApp.controller.PlayerController;
import com.yourapp.MusicApp.model.Song;
import com.yourapp.MusicApp.controller.LibraryController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MusicAppApplication extends Application {

    private Stage primaryStage;
    private Scene playerScene;
    private Scene libraryScene;
    private Scene settingsScene;
    private PlayerController playerController;  // Lưu playerController
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        // Tải trang FXML cho Home
        FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("/fxml/player.fxml"));
        AnchorPane playerPage = homeLoader.load();
        playerScene = new Scene(playerPage);
        // Lấy controller PlayerController
        playerController = homeLoader.getController();
        playerController.setApp(this);

        // Truyền tham chiếu primaryStage vào controller HomeController
        PlayerController playerController = homeLoader.getController();
        playerController.setApp(this);

        // Tải trang FXML cho Library
        FXMLLoader libraryLoader = new FXMLLoader(getClass().getResource("/fxml/library.fxml"));
        VBox libraryPage = libraryLoader.load();
        libraryScene = new Scene(libraryPage);

        // Truyền tham chiếu primaryStage vào controller LibraryController
        LibraryController libraryController = libraryLoader.getController();
        libraryController.setApp(this);

        // Tải trang FXML cho Settings
        FXMLLoader settingsLoader = new FXMLLoader(getClass().getResource("/fxml/settings.fxml"));
        StackPane settingsPage = settingsLoader.load();
        settingsScene = new Scene(settingsPage);

        // Đặt Scene mặc định là Home
        primaryStage.setTitle("Music App");
        primaryStage.setScene(libraryScene);
        
        primaryStage.show();
    }
// báo lỗi
    public void showErrorDialog(String title, String message) {
        // Tạo đối tượng Alert để hiển thị thông báo lỗi
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);  // Tiêu đề của thông báo
        alert.setHeaderText(null);  // Tiêu đề phụ, không cần thiết trong trường hợp này
        alert.setContentText(message);  // Nội dung lỗi sẽ hiển thị

        // Hiển thị thông báo lỗi
        alert.showAndWait();
    }
    // Chuyển đến trang Home
    public void showPlayerPage() {
        primaryStage.setScene(playerScene);
    }

    // Chuyển đến trang Library
    public void showLibraryPage() {
        primaryStage.setScene(libraryScene);
    }

    // Chuyển đến trang Settings
    public void showSettingsPage() {
        primaryStage.setScene(settingsScene);
    }
    
    // Kiểm tra nếu có bài hát đang phát
    public boolean isSongPlaying() {
        return playerController != null && playerController.isPlaying();
    }

    // Dừng bài hát đang phát
    public void stopCurrentSong() {
        if (playerController != null) {
            playerController.handleStop();
        }
    }

    public void showPlayerAndPlay(Song song) {
        try {
            // Nếu có bài hát đang phát, dừng nó
            if (isSongPlaying()) {
                stopCurrentSong();
            }

            // Chuyển sang giao diện Player
            primaryStage.setScene(playerScene);

            // Phát bài hát từ thư viện
            if (song != null && song.getFilePath() != null) {
                playerController.handlePlaySongFromFile(song.getFilePath());
            } else {
                showErrorDialog("Lỗi", "Không có bài hát hợp lệ để phát.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorDialog("Lỗi khi phát nhạc", "Không thể phát bài hát mới.");
        }
    }
    

    public static void main(String[] args) {
        launch(args);
    }
}
