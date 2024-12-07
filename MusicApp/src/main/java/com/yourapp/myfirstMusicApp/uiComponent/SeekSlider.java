package com.yourapp.myfirstMusicApp.uiComponent;

import com.yourapp.myfirstMusicApp.AudioPlayer;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

public class SeekSlider extends Slider {
    
    private AudioPlayer audioPlayer;

    // Constructor mặc định (bắt buộc để FXML sử dụng)
    public SeekSlider() {
        super(); // Gọi constructor của Slider
        initializeSlider();
     // Lắng nghe khi AudioPlayer có MediaPlayer
        AudioPlayer.getInstance().addMediaPlayerListener(mediaPlayer -> bindAudioPlayer(AudioPlayer.getInstance()));
    }

    // Constructor hiện tại với tham số (nếu bạn muốn dùng nó trong code Java)
    public SeekSlider(Slider slider) {
        super();
        // Chuyển logic cần thiết từ slider truyền vào thành thuộc tính của chính nó
        this.setMin(slider.getMin());
        this.setMax(slider.getMax());
        this.setValue(slider.getValue());
        initializeSlider();
    }

    // Liên kết slider với AudioPlayer
    public void bindAudioPlayer(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
        if (audioPlayer != null ) {
            audioPlayer.setOnProgressListener(currentTime -> {
                if (!this.isValueChanging() && audioPlayer.getMediaPlayer() != null ) {
                    double progress = currentTime / audioPlayer.getTotalDuration();
                    this.setValue(progress * 100);
                }
            });
            this.setMax(100);
        }
    }

    // Xử lý tua khi thả chuột
    private void handleSeek() {
    	AudioPlayer audioPlayer = AudioPlayer.getInstance(); // Lấy Singleton
        if (audioPlayer != null) {
            double seekTime = this.getValue() / 100 * audioPlayer.getTotalDuration();
            audioPlayer.seek(seekTime);
        }
    }

    // Xử lý tua khi click
    private void handleSeekByClick(MouseEvent event) {
    	 AudioPlayer audioPlayer = AudioPlayer.getInstance(); // Lấy Singleton
        if (audioPlayer != null) {
            double clickPosition = event.getX() / this.getWidth();
            double seekTime = clickPosition * audioPlayer.getTotalDuration();
            audioPlayer.seek(seekTime);
        }
    }

    // Khởi tạo slider và các listener
    private void initializeSlider() {
    	
        this.setOnMouseReleased(event -> handleSeek());
        this.setOnMouseClicked(this::handleSeekByClick);
        this.valueChangingProperty().addListener((observable, wasChanging, isChanging) -> {
            if (!isChanging) {
                handleSeek();
            }
        });
        
    }
    // Phương thức reset lại trạng thái của SeekSlider
    public void reset() {
        this.setValue(0); // Đặt giá trị slider về 0
        

    }
 // Phương thức kích hoạt lại slider khi bài hát mới bắt đầu
    public void activate() {
        this.setDisable(false); // Kích hoạt slider
        if (audioPlayer != null) {
            audioPlayer.setOnProgressListener(currentTime -> {
                if (!this.isValueChanging()) {
                    double progress = currentTime / audioPlayer.getTotalDuration();
                    this.setValue(progress * 100);
                }
            });
        }
    }
}
