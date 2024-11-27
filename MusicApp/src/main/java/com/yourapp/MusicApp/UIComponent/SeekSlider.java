package com.yourapp.MusicApp.UIComponent;

import com.yourapp.MusicApp.AudioPlayer;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

public class SeekSlider {
    private final Slider slider;
    private AudioPlayer audioPlayer;

    public SeekSlider(Slider slider) {
        this.slider = slider;
        initializeSlider();
    }

    // Liên kết slider với AudioPlayer
    public void bindAudioPlayer(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
        if (audioPlayer != null) {
            audioPlayer.setOnProgressListener(currentTime -> {
                if (!slider.isValueChanging()) {
                    double progress = currentTime / audioPlayer.getTotalDuration();
                    slider.setValue(progress * 100);
                }
            });
            slider.setMax(100);
        }
    }

    // Xử lý tua khi thả chuột
    private void handleSeek() {
        if (audioPlayer != null) {
            double seekTime = slider.getValue() / 100 * audioPlayer.getTotalDuration();
            audioPlayer.seek(seekTime);
        }
    }

    // Xử lý tua khi click
    private void handleSeekByClick(MouseEvent event) {
        if (audioPlayer != null) {
            double clickPosition = event.getX() / slider.getWidth();
            double seekTime = clickPosition * audioPlayer.getTotalDuration();
            audioPlayer.seek(seekTime);
        }
    }

    // Khởi tạo slider và các listener
    private void initializeSlider() {
        slider.setOnMouseReleased(event -> handleSeek());
        slider.setOnMouseClicked(this::handleSeekByClick);
        slider.valueChangingProperty().addListener((observable, wasChanging, isChanging) -> {
            if (!isChanging) {
                handleSeek();
            }
        });
    }

    // Trả về slider nếu cần truy cập bên ngoài
    public Slider getSlider() {
        return slider;
    }
}
