package com.yourapp.myfirstMusicApp.controller;

import com.yourapp.myfirstMusicApp.MusicAppApplication;
import com.yourapp.myfirstMusicApp.SongManager;
import com.yourapp.myfirstMusicApp.model.Song;
import com.yourapp.myfirstMusicApp.repository.SongRepository;
import com.yourapp.myfirstMusicApp.uiComponent.PlayButton;
import com.yourapp.myfirstMusicApp.utils.TimeUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class SongController {

    @FXML
    private Label songArtist;

    @FXML
    private Label songDuration;

    @FXML
    private ImageView songImg;

    @FXML
    private Label songName;

    @FXML
    private PlayButton playButton; // Play button mới thêm vào

    private Song songData;  // Dữ liệu bài hát

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

        // Cập nhật PlayButton với filePath của bài hát
        playButton.setFilePath(song.getFilePath());
    }

    @FXML
    private void handleSongClick() {
        // Truyền danh sách bài hát và bài hát đã chọn vào SongManager
        SongManager.getInstance().setSongList(SongRepository.findAll());

        // Gán bài hát đã chọn vào SongManager
        SongManager.getInstance().setSelectedSong(this.songData);
        MusicAppApplication.getInstance().showPlayerPage();
        MusicAppApplication.getInstance().getPlayerController().playAllSongs(SongManager.getInstance().getSongList(), SongManager.getInstance().getSelectedSong());
        
    }
}
