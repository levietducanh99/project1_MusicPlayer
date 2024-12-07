package com.yourapp.myfirstMusicApp.uiComponent;

import com.yourapp.myfirstMusicApp.AudioPlayer;
import com.yourapp.myfirstMusicApp.MusicAppApplication;
import com.yourapp.myfirstMusicApp.controller.PlayerController;
import javafx.fxml.FXML;
import lombok.Data;
import javafx.event.ActionEvent;

@Data
public class SmallPlayer {

    @FXML
    private NextButton nextButton;

    @FXML
    private PauseButton pauseButton;

    @FXML
    private SeekSlider seekSlider;

    private PlayerController playerController;
    private AudioPlayer audioPlayer;

    public SmallPlayer() {
        // Lấy instance của PlayerController đã tồn tại
        playerController = PlayerController.getInstance();
    }

    public void setApp(MusicAppApplication musicAppApplication) {
        // Gán ứng dụng cho PlayerController nếu cần thiết
        playerController.setApp(musicAppApplication);
        
        // Liên kết PlayerController với các custom components
    //    nextButton.bindPlayerController(PlayerController.getInstance());  // Liên kết nextButton với playerController
  
    //    seekSlider.bindAudioPlayer(AudioPlayer.getInstance());  // Liên kết seekSlider với AudioPlayer
    }
    public void initialize() {
        AudioPlayer audioPlayer = AudioPlayer.getInstance();
   //     seekSlider.bindAudioPlayer(audioPlayer);
        // Đăng ký lắng nghe nếu MediaPlayer được tạo sau đó
        audioPlayer.addMediaPlayerListener(mediaPlayer -> seekSlider.bindAudioPlayer(audioPlayer));
    }
    
    
    
    @FXML
    void handlePlay(ActionEvent event) {
        // Gọi phương thức từ PlayerController để phát nhạc
        playerController.handlePlaySongFromEntity(playerController.getCurrentSong());
    }

    @FXML
    void handleStop(ActionEvent event) {
        // Gọi phương thức dừng từ PlayerController
        playerController.handleStop();
    }

    @FXML
    void handleNext(ActionEvent event) {
        // Gọi phương thức chuyển bài từ PlayerController
        playerController.playNextSong();
    }

    // Thêm các phương thức khác nếu cần thiết
    public void updateSeekSlider() {
        // Cập nhật giá trị của SeekSlider dựa trên tiến trình của bài hát
        if (audioPlayer != null) {
            double progress = audioPlayer.getTotalDuration() > 0 ?
                    (audioPlayer.getMediaPlayer().getCurrentTime().toSeconds() / audioPlayer.getTotalDuration()) * 100 : 0;
            seekSlider.setValue(progress);
        }
    }
}
