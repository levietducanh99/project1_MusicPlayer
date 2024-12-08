package com.yourapp.myfirstMusicApp;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;

public class MainLauncher {
    public static void main(String[] args) {
        // Tắt log WARNING cho JAudioTagger
        Logger jaudiotaggerLogger = Logger.getLogger("org.jaudiotagger");
        jaudiotaggerLogger.setLevel(java.util.logging.Level.WARNING);

        // Tắt log WARNING cho JavaFX (tất cả các log từ javafx.fxml và javafx sẽ được tắt)
        Logger javafxLogger = Logger.getLogger("javafx.fxml");
        javafxLogger.setLevel(Level.SEVERE); // Chỉ hiển thị SEVERE và bỏ qua INFO, WARNING

        // Tắt log cho package javafx
        Logger.getLogger("javafx").setLevel(Level.SEVERE);

        // Gọi phương thức launch() của lớp MainApplication
        Application.launch(MusicAppApplication.class, args);
    }
}
