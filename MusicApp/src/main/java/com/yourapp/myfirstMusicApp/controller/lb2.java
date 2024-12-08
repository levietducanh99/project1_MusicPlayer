package com.yourapp.myfirstMusicApp.controller;

import com.yourapp.myfirstMusicApp.model.Song;
import com.yourapp.myfirstMusicApp.repository.SongRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.List;

public class lb2 {

    @FXML
    private VBox songContainer;  // VBox chứa các bài hát trong ScrollPane

    private SongRepository songRepository = new SongRepository();

    // Phương thức để thêm các bài hát vào songContainer
    public void initialize() {
    	 List<Song> songs = songRepository.findAll(); // Lấy danh sách tất cả bài hát từ cơ sở dữ liệu

         for (Song song : songs) {
             try {
                 // Load Song.fxml cho mỗi bài hát
                 FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Song.fxml"));
                 HBox songVBox = loader.load(); // Load song.fxml

                 // Lấy controller của Song.fxml và thiết lập dữ liệu cho bài hát
                 SongController songController = loader.getController();
                 songController.setSongData(song);

                 // Thêm song vào songContainer
                 songContainer.getChildren().add(songVBox);
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
     }
}
