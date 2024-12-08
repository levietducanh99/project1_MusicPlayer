package com.yourapp.myfirstMusicApp;

import com.yourapp.myfirstMusicApp.controller.SongController;
import com.yourapp.myfirstMusicApp.model.Song;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load library2.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/library2.fxml"));
            Parent root = loader.load();

            // Lấy VBox songContainer từ ScrollPane
            VBox songContainer = (VBox) loader.getNamespace().get("songContainer");

            // Tạo danh sách các bài hát mẫu
            List<Song> songList = Arrays.asList(
                    new Song("Sample Song 1", "Artist 1", "F:/Music/Ba Da Bum - B Ray - Bài hát, lyrics_2.mp3", 200),
                    new Song("Sample Song 2", "Artist 2", "F:/Music/Ba Da Bum - B Ray - Bài hát, lyrics_2.mp3", 250),
                    new Song("Sample Song 3", "Artist 3", "F:/Music/Ba Da Bum - B Ray - Bài hát, lyrics_2.mp3", 180)
            );

            // Thêm từng bài hát vào songContainer
            for (Song song : songList) {
                // Load Song.fxml
                FXMLLoader songLoader = new FXMLLoader(getClass().getResource("/fxml/Song.fxml"));
                Node songNode = songLoader.load();

                // Lấy controller và thiết lập dữ liệu bài hát
                SongController songController = songLoader.getController();
                songController.setSongData(song);

                // Thêm node vào songContainer
                songContainer.getChildren().add(songNode);
            }

            // Set up scene
            Scene scene = new Scene(root, 400, 600);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Song List Test");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
