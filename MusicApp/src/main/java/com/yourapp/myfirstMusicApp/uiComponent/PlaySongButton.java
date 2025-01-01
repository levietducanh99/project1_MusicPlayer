package com.yourapp.myfirstMusicApp.uiComponent;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import lombok.Data;
import lombok.Setter;

import com.yourapp.myfirstMusicApp.AudioPlayer;
import com.yourapp.myfirstMusicApp.model.History;

public class PlaySongButton extends Button {

    private String filePath; // Đường dẫn đến file MP3

    // Constructor mặc định (bắt buộc để FXML sử dụng)
    public PlaySongButton() {
        super(); // Gọi constructor của Button
        initializeButton();
    }

    // Constructor hiện tại với tham số (nếu bạn muốn dùng nó trong code Java)
    public PlaySongButton(String filePath) {
        super();
        this.filePath = filePath;
        initializeButton();
    }

    // Thiết lập các hành động khi nhấn nút
    private void initializeButton() {
        this.setOnMouseClicked(this::handlePlay);
 
    }

    // Xử lý khi nhấn nút Play
    private void handlePlay(MouseEvent event) {
    	 if (filePath == null || filePath.isEmpty()) {
    	        System.err.println("File path is null or empty. Cannot play song.");
    	        return;
    	    }
        AudioPlayer audioPlayer = AudioPlayer.getInstance(); // Lấy Singleton của AudioPlayer

        if (audioPlayer != null) {
            // Nếu có bài hát đang phát, dừng lại
            if (audioPlayer.isPlaying()) {
                audioPlayer.stop(); // Dừng bài hát hiện tại
            }

            // Cập nhật filePath và phát bài hát mới
            audioPlayer.play(filePath); // Chạy bài hát theo đường dẫn
            // Lưu lịch sử nghe nhạc vào cơ sở dữ liệu
            History.savePlayHistory(filePath); // Gọi phương thức tĩnh từ class History
        }
    }

    // Getter và Setter cho filePath
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
