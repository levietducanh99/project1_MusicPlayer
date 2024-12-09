package com.yourapp.myfirstMusicApp.uiComponent;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import com.yourapp.myfirstMusicApp.AudioPlayer;
import com.yourapp.myfirstMusicApp.MusicAppApplication;
import com.yourapp.myfirstMusicApp.controller.PlayerController;

public class NextButton extends Button {

    private PlayerController playerController;

    // Constructor mặc định (bắt buộc để FXML sử dụng)
    public NextButton() {
        super(); // Gọi constructor của Button
        initializeButton();
    }

    // Constructor với tham số PlayerController để xử lý nút Next
    public NextButton(PlayerController playerController) {
        super();
        this.playerController = playerController;
        initializeButton();
    }

    // Thiết lập hành động cho nút Next
    private void initializeButton() {
    	this.playerController= MusicAppApplication.getInstance().getPlayerController();
        this.setOnMouseClicked(this::handleNext); // Đặt sự kiện cho nút
        
    }

    // Xử lý khi nhấn nút Next
    private void handleNext(MouseEvent event) {
    	  AudioPlayer.getInstance().stop(); // Dừng bài hát hiện tại
        if (playerController != null) {
            playerController.playNextSong();  // Gọi phương thức playNextSong từ PlayerController
        }
    }

    // Liên kết PlayerController với NextButton
    public void bindPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }
}
