package com.yourapp.myfirstMusicApp.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.yourapp.myfirstMusicApp.MusicAppApplication;
import com.yourapp.myfirstMusicApp.SongManager;
import com.yourapp.myfirstMusicApp.model.Song;
import com.yourapp.myfirstMusicApp.repository.SongRepository;
import com.yourapp.myfirstMusicApp.uiComponent.PlayButton;
import com.yourapp.myfirstMusicApp.uiComponent.PlaylistSelectionController;
import com.yourapp.myfirstMusicApp.utils.TimeUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SongControllerV2 {

    @FXML
    private Label songArtist;

    @FXML
    private Label songDuration;

    @FXML
    private ImageView songImg;

    @FXML
    private Label songName;

   
    private Song songData;  // Dữ liệu bài hát
    private List<Song> currentSongList;

    public void setSongList(List<Song> songList) {
        this.currentSongList = songList;
    }
    public void setSongData(Song song) {
        // Lưu bài hát vào songData để sử dụng sau
        this.songData = song;

        // Đặt tên bài hát
        songName.setText(song.getTitle());

        // Đặt nghệ sĩ
        songArtist.setText(song.getArtist());

        // Đặt thời lượng đã format
        songDuration.setText(TimeUtils.formatDuration(song.getDuration()));

        // Lấy ảnh từ metadata và đặt vào ImageView
        Image image = song.extractAlbumArt();
        songImg.setImage(image);
   
       
    }

    @FXML
    private void handleSongClick() {
        if (currentSongList == null) {
            throw new IllegalStateException("Song list is not set for this controller!");
        }

        // Truyền danh sách và bài hát được chọn vào SongManager
        SongManager.getInstance().setSongList(currentSongList);
        SongManager.getInstance().setSelectedSong(this.songData);

        // Hiển thị trang Player và phát nhạc
        MusicAppApplication.getInstance().showPlayerPage();
        MusicAppApplication.getInstance().getPlayerController().playAllSongs(currentSongList, songData);
    }

}
