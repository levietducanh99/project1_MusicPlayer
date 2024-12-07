package com.yourapp.myfirstMusicApp.uiComponent;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import lombok.Data;
import lombok.Setter;

import com.yourapp.myfirstMusicApp.AudioPlayer;

public class PauseButton extends Button {
    
  
    private boolean isPaused = false;

    // Constructor mặc định (bắt buộc để FXML sử dụng)
    public PauseButton() {
        super(); // Gọi constructor của Button
        initializeButton();
    }

    // Constructor hiện tại với tham số (nếu bạn muốn dùng nó trong code Java)
    public PauseButton(AudioPlayer audioPlayer) {
        super();
       
        initializeButton();
    }

    // Thiết lập các hành động khi nhấn nút
    private void initializeButton() {
        this.setOnMouseClicked(this::handlePause);
    }

    // Liên kết AudioPlayer với PauseButton


    // Xử lý khi nhấn nút Pause
    private void handlePause(MouseEvent event) {
    	 AudioPlayer audioPlayer = AudioPlayer.getInstance(); // Lấy Singleton
        if (audioPlayer != null) {
        	audioPlayer.pause(); // Tạm dừng nhạc hoặc tiếp tục phát
            if (isPaused) {
            	this.getStyleClass().remove("paused");
                
            } else {
            	
            	this.getStyleClass().add("paused");
                
            }
            isPaused = !isPaused; // Đảo ngược trạng thái
        }
    }

    // Trả về trạng thái paused của nút
    public boolean isPaused() {
        return isPaused;
    }
  
    // Trả về trạng thái paused của nút
    public void setisPaused(Boolean aBoolean) {
        this.isPaused = aBoolean;
    }
    public void resetPauseButton() {
        this.isPaused = false;  // Đặt lại trạng thái paused
        this.getStyleClass().remove("paused");  // Xóa kiểu dáng paused
    }

}
