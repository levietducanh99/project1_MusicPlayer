package com.yourapp.myfirstMusicApp.loader;

import com.yourapp.myfirstMusicApp.MusicAppApplication;
import com.yourapp.myfirstMusicApp.uiComponent.SmallPlayer;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class SmallPlayerLoader {

    // Lưu các instance riêng biệt cho từng Scene
    private static HBox librarySmallPlayer;
    private static HBox playerSmallPlayer;

    // Trả về SmallPlayer cho Library
    public static HBox getLibrarySmallPlayer(MusicAppApplication app) {
        if (librarySmallPlayer == null) {
            librarySmallPlayer = loadSmallPlayer(app);
        }
        return librarySmallPlayer;
    }

    // Trả về SmallPlayer cho Player
    public static HBox getPlayerSmallPlayer(MusicAppApplication app) {
        if (playerSmallPlayer == null) {
            playerSmallPlayer = loadSmallPlayer(app);
        }
        return playerSmallPlayer;
    }

    // Tạo mới một SmallPlayer từ FXML
    private static HBox loadSmallPlayer(MusicAppApplication app) {
        try {
            FXMLLoader loader = new FXMLLoader(SmallPlayerLoader.class.getResource("/fxml/smallPlayer.fxml"));
            HBox smallPlayerBox = loader.load();
            SmallPlayer controller = loader.getController();
            controller.setApp(app); // Gán ứng dụng cho SmallPlayer
            return smallPlayerBox;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load SmallPlayer FXML");
        }
    }
}
