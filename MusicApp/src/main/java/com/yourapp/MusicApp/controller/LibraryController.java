package com.yourapp.MusicApp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import com.yourapp.MusicApp.MusicAppApplication;
import com.yourapp.MusicApp.model.Song;
import com.yourapp.MusicApp.repository.SongRepository;

import java.io.File;
import java.io.IOException;

public class LibraryController {

    private MusicAppApplication app;
    
    private SongRepository songRepository;

    // Constructor to inject SongRepository
    public LibraryController() {
        this.songRepository = new SongRepository(); // Assuming SongRepository is properly initialized
    }

    @FXML
    private void handleGoBackToHome(ActionEvent event) {
        app.showHomePage(); // Quay lại trang Home
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
}
