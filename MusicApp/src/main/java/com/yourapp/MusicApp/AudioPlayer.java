package com.yourapp.MusicApp;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.function.Consumer;

public class AudioPlayer {
    private MediaPlayer mediaPlayer;

    // Phương thức phát nhạc
    public void play(String filePath) {
        stop(); // Dừng bài hát hiện tại trước khi phát bài mới

        // Tạo MediaPlayer từ file
        Media media = new Media(new File(filePath).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play(); // Bắt đầu phát
    }

	    // Phương thức tạm dừng hoặc tiếp tục phát
	    public void pause() {
	        if (mediaPlayer != null) {
	            MediaPlayer.Status status = mediaPlayer.getStatus();
	            if (status == MediaPlayer.Status.PLAYING) {
	                mediaPlayer.pause(); // Tạm dừng
	            } else if (status == MediaPlayer.Status.PAUSED) {
	                mediaPlayer.play(); // Tiếp tục phát
	            }
	        }
	    }

    // Phương thức dừng phát nhạc
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop(); // Dừng phát
            mediaPlayer = null; // Giải phóng MediaPlayer
        }
    }
    public void speedChange(double value) {
        if (mediaPlayer != null) {
            mediaPlayer.setRate(value); // chỉnh tốc độ phát
            
        }
    }
    public double getTotalDuration() {
        return mediaPlayer.getTotalDuration().toSeconds(); // Tổng thời gian bài hát
    }

    public void seek(double seconds) {
        mediaPlayer.seek(Duration.seconds(seconds)); // Tua đến thời gian
    }

    public void setOnProgressListener(Consumer<Double> listener) {
        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            listener.accept(newTime.toSeconds());
        });
    }
    public double getCurrentPosition() {
        return mediaPlayer.getCurrentTime().toSeconds();
    }

}