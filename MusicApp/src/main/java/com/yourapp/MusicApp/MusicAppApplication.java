package com.yourapp.MusicApp;

import com.yourapp.MusicApp.controller.HomeController;
import com.yourapp.MusicApp.controller.LibraryController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MusicAppApplication extends Application {

    private Stage primaryStage;
    private Scene homeScene;
    private Scene libraryScene;
    private Scene settingsScene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        // Tải trang FXML cho Home
        FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("/home.fxml"));
        StackPane homePage = homeLoader.load();
        homeScene = new Scene(homePage);

        // Truyền tham chiếu primaryStage vào controller HomeController
        HomeController homeController = homeLoader.getController();
        homeController.setApp(this);

        // Tải trang FXML cho Library
        FXMLLoader libraryLoader = new FXMLLoader(getClass().getResource("/library.fxml"));
        VBox libraryPage = libraryLoader.load();
        libraryScene = new Scene(libraryPage);

        // Truyền tham chiếu primaryStage vào controller LibraryController
        LibraryController libraryController = libraryLoader.getController();
        libraryController.setApp(this);

        // Tải trang FXML cho Settings
        FXMLLoader settingsLoader = new FXMLLoader(getClass().getResource("/settings.fxml"));
        StackPane settingsPage = settingsLoader.load();
        settingsScene = new Scene(settingsPage);

        // Đặt Scene mặc định là Home
        primaryStage.setTitle("Music App");
        primaryStage.setScene(homeScene);
        primaryStage.show();
    }


    // Chuyển đến trang Home
    public void showHomePage() {
        primaryStage.setScene(homeScene);
    }

    // Chuyển đến trang Library
    public void showLibraryPage() {
        primaryStage.setScene(libraryScene);
    }

    // Chuyển đến trang Settings
    public void showSettingsPage() {
        primaryStage.setScene(settingsScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
