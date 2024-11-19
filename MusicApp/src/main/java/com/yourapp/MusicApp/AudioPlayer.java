package com.yourapp.MusicApp;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AudioPlayer {

    private Clip clip;
    private Player mp3Player;

    // Phương thức để phát file âm thanh (hỗ trợ định dạng .wav và .mp3)
    public void play(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException, JavaLayerException {
        if (filePath.toLowerCase().endsWith(".wav")) {
            playWav(filePath);
        } else if (filePath.toLowerCase().endsWith(".mp3")) {
            playMp3(filePath);
        } else {
            throw new UnsupportedAudioFileException("Unsupported audio file format: " + filePath);
        }
    }

    private void playWav(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File audioFile = new File(filePath);
        
        // Kiểm tra nếu file tồn tại
        if (!audioFile.exists()) {
            throw new IOException("File không tồn tại: " + filePath);
        }
        
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        AudioFormat format = audioStream.getFormat();
        
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        clip = (Clip) AudioSystem.getLine(info);
        clip.open(audioStream);
        
        clip.start();  // Bắt đầu phát nhạc
    }

    private void playMp3(String filePath) throws JavaLayerException, IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        mp3Player = new Player(fileInputStream);
        new Thread(() -> {
            try {
                mp3Player.play();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Phương thức dừng phát nhạc
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();  // Dừng phát nhạc
        } else if (mp3Player != null) {
            mp3Player.close();  // Dừng phát nhạc MP3
        }
    }

    // Phương thức phát nhạc lặp lại liên tục (không áp dụng cho MP3 vì JLayer không hỗ trợ lặp)
    public void loop() {
        // Bạn có thể cài đặt logic để lặp lại nhạc WAV
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);  // Phát nhạc lặp lại liên tục
        }
    }


}