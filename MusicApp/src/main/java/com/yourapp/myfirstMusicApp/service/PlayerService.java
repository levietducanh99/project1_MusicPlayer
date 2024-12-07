package com.yourapp.myfirstMusicApp.service;

import com.yourapp.myfirstMusicApp.AudioPlayer;
import com.yourapp.myfirstMusicApp.model.History;
import com.yourapp.myfirstMusicApp.model.Song;
import javafx.scene.image.Image;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import lombok.Data;

import java.util.function.Consumer;
@Data
public class PlayerService {

    private AudioPlayer audioPlayer;
    private int currentSongIndex = 0;
    private ObservableList<Song> currentPlaylist;
    private boolean isPlaying = false;

    public PlayerService() {
        audioPlayer = new AudioPlayer();
    }

    public void playSongFromFile(String filePath) {
        if (filePath != null && !filePath.isEmpty()) {
            audioPlayer.stop();
            audioPlayer.play(filePath);
            isPlaying = true;
            // Lưu lịch sử phát nhạc
            History.savePlayHistory(filePath);
        }
    }

    public void setOnEndOfMedia(Runnable action) {
        audioPlayer.setOnEndOfMedia(action);
    }

    public void pause() {
        if (audioPlayer != null) {
            audioPlayer.pause();
            isPlaying = !isPlaying;
        }
    }

    public void stop() {
        if (audioPlayer != null) {
            audioPlayer.stop();
            isPlaying = false;
        }
    }

    public void changeSpeed(double speed) {
        if (audioPlayer != null) {
            audioPlayer.speedChange(speed);
        }
    }

    public void seek(double seconds) {
        if (audioPlayer != null) {
            audioPlayer.seek(seconds);
        }
    }

    public double getTotalDuration() {
        return audioPlayer.getTotalDuration();
    }

    public double getCurrentPosition() {
        return audioPlayer.getCurrentPosition();
    }

    public void setOnProgressListener(Consumer<Double> listener) {
        if (audioPlayer != null) {
            audioPlayer.setOnProgressListener(listener);
        }
    }

    public Image extractAlbumArt() {
        return audioPlayer.extractAlbumArt();
    }

    public void setPlaylist(ObservableList<Song> playlist) {
        this.currentPlaylist = playlist;
    }

    public void playNextSong() {
        if (currentPlaylist != null && !currentPlaylist.isEmpty()) {
            currentSongIndex = (currentSongIndex + 1) % currentPlaylist.size();
            playSongFromEntity(currentPlaylist.get(currentSongIndex));
        }
    }

    public void playSongFromEntity(Song song) {
        if (song != null) {
            playSongFromFile(song.getFilePath());
        }
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public ObservableList<Song> getCurrentPlaylist() {
        return currentPlaylist;
    }
}
