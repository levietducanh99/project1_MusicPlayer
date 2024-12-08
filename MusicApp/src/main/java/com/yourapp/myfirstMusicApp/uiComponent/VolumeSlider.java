package com.yourapp.myfirstMusicApp.uiComponent;

import com.yourapp.myfirstMusicApp.AudioPlayer;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

public class VolumeSlider extends Slider {

    private AudioPlayer audioPlayer;

    // Constructor mặc định (bắt buộc để FXML sử dụng)
    public VolumeSlider() {
        super(); // Gọi constructor của Slider
        initializeSlider();
        // Lắng nghe khi AudioPlayer có MediaPlayer
        AudioPlayer.getInstance().addMediaPlayerListener(mediaPlayer -> bindAudioPlayer(AudioPlayer.getInstance()));
    }

    // Constructor với tham số (nếu bạn muốn dùng nó trong code Java)
    public VolumeSlider(Slider slider) {
        super();
        this.setMin(slider.getMin());
        this.setMax(slider.getMax());
        this.setValue(slider.getValue());
        initializeSlider();
    }

    // Liên kết slider với AudioPlayer để điều chỉnh âm lượng
    public void bindAudioPlayer(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
        if (audioPlayer != null) {
            this.setValue(audioPlayer.getVolume() * 100); // Gán giá trị hiện tại của âm lượng vào slider
        }
    }

    // Xử lý thay đổi âm lượng khi người dùng kéo slider
    private void handleVolumeChange() {
        if (audioPlayer != null) {
            double volume = this.getValue() / 100; // Chuyển giá trị slider thành âm lượng
            audioPlayer.setVolume(volume); // Cập nhật âm lượng của AudioPlayer
        }
    }

    // Xử lý thay đổi âm lượng khi click vào slider
    private void handleVolumeClick(MouseEvent event) {
        if (audioPlayer != null) {
            double clickPosition = event.getX() / this.getWidth();
            double volume = clickPosition; // Chuyển vị trí click thành âm lượng
            audioPlayer.setVolume(volume);
            this.setValue(volume * 100); // Cập nhật lại giá trị slider
        }
    }

    // Khởi tạo slider và các listener
    private void initializeSlider() {
        this.setOnMouseReleased(event -> handleVolumeChange());
        this.setOnMouseClicked(this::handleVolumeClick);
        this.valueChangingProperty().addListener((observable, wasChanging, isChanging) -> {
            if (!isChanging) {
                handleVolumeChange();
            }
        });
    }

    // Phương thức reset lại trạng thái của VolumeSlider
    public void reset() {
        this.setValue(50); // Đặt giá trị âm lượng về mức trung bình
        if (audioPlayer != null) {
            audioPlayer.setVolume(0.5); // Đặt âm lượng của AudioPlayer về mức trung bình
        }
    }

    // Phương thức kích hoạt lại slider khi có AudioPlayer mới hoặc bài hát mới
    public void activate() {
        this.setDisable(false); // Kích hoạt slider
        if (audioPlayer != null) {
            this.setValue(audioPlayer.getVolume() * 100); // Gán giá trị âm lượng của AudioPlayer vào slider
        }
    }
}
