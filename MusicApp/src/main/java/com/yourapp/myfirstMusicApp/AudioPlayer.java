package com.yourapp.myfirstMusicApp;

import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import lombok.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
@Data
public class AudioPlayer {
    private MediaPlayer mediaPlayer;
    private String currentFilePath;
    private final DoubleProperty currentProgress = new SimpleDoubleProperty(0);
  //  private final List<Runnable> readyListeners = new ArrayList<>();
    private final List<Consumer<MediaPlayer>> mediaPlayerListeners = new ArrayList<>();
 // Singleton instance
    private static AudioPlayer instance;
 // Public method to provide access to the instance
    public static synchronized AudioPlayer getInstance() {
        if (instance == null) {
            instance = new AudioPlayer();
           
        }
        return instance;
    }
    
    // Thêm listener lắng nghe khi MediaPlayer sẵn sàng
    public void addMediaPlayerListener(Consumer<MediaPlayer> listener) {
        if (mediaPlayer != null) {
            listener.accept(mediaPlayer); // Nếu đã có mediaPlayer, gọi ngay lập tức
        } else {
            mediaPlayerListeners.add(listener); // Đợi MediaPlayer sẵn sàng
        }
    }

    // Thông báo tất cả các listener
    private void notifyMediaPlayerListeners() {
        for (Consumer<MediaPlayer> listener : mediaPlayerListeners) {
            listener.accept(mediaPlayer);
        }
        mediaPlayerListeners.clear(); // Xóa listener sau khi thông báo
    }
    
    
    
    // end of setup
    
    // Phương thức phát nhạc
    public void play(String filePath) {
        stop(); // Dừng bài hát hiện tại trước khi phát bài mới
        this.currentFilePath = filePath;
        // Tạo MediaPlayer từ file
        Media media = new Media(new File(filePath).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        notifyMediaPlayerListeners(); // Thông báo MediaPlayer đã sẵn sàng
        mediaPlayer.play(); // Bắt đầu phát
        setOnProgressListener();
    }
    // Phương thức thiết lập hành động khi bài hát kết thúc
    public void setOnEndOfMedia(Runnable action) {
        if (mediaPlayer != null) {
            mediaPlayer.setOnEndOfMedia(action);
        }
    }
    
    
 // Kiểm tra xem bài hát có đang phát hay không
    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
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
    public DoubleProperty currentProgressProperty() {
        return currentProgress;
    }

    public void setOnProgressListener() {
        if (mediaPlayer != null) {
            mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
                double progress = newValue.toSeconds() / getTotalDuration();
                currentProgress.set(progress*100); // Thông báo thay đổi
            });
        }
    }
    public double getCurrentPosition() {
        return mediaPlayer.getCurrentTime().toSeconds();
    }
    public Image extractAlbumArt() {
        if (currentFilePath == null || currentFilePath.isEmpty()) {
            return null; // Không có file nào đang phát
        }

        try {
            // Đọc file MP3 và metadata
            AudioFile audioFile = AudioFileIO.read(new File(currentFilePath));
            Tag tag = audioFile.getTag();

            if (tag != null && tag.hasField(FieldKey.COVER_ART)) {
                // Lấy dữ liệu ảnh bìa
                byte[] imageData = tag.getFirstArtwork().getBinaryData();
                return new Image(new ByteArrayInputStream(imageData)); // Trả về ảnh dạng Image
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Không tìm thấy ảnh bìa
    }
    
}