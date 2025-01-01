package com.yourapp.myfirstMusicApp.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.yourapp.myfirstMusicApp.MusicAppApplication;
import com.yourapp.myfirstMusicApp.SongManager;
import com.yourapp.myfirstMusicApp.model.Song;
import com.yourapp.myfirstMusicApp.repository.SongPlaylistRepository;
import com.yourapp.myfirstMusicApp.repository.SongRepository;
import com.yourapp.myfirstMusicApp.uiComponent.PlayButton;
import com.yourapp.myfirstMusicApp.uiComponent.PlaySongButton;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lombok.Data;
@Data
public class SongControllerV3 {

    @FXML
    private Label songArtist;

    @FXML
    private Label songDuration;

    @FXML
    private ImageView songImg;

    @FXML
    private Label songName;

    @FXML
    private PlaySongButton playSongButton; // Play button mới thêm vào
    @FXML
    private Button likeButton;
    @FXML HBox songHBox;
    private Song songData;  // Dữ liệu bài hát
    private List<Song> currentSongList;
    private long currentPlaylistId;

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
     // Cập nhật giao diện của nút like dựa trên trạng thái isFavourite
        updateLikeButton();
        // Cập nhật PlayButton với filePath của bài hát
        playSongButton.setFilePath(song.getFilePath());
    }

    @FXML
    private void handleSongClick() {
    	// Truyền danh sách và bài hát được chọn vào SongManager
        SongManager.getInstance().setSongList(currentSongList);

        // Gán bài hát đã chọn vào SongManager
        SongManager.getInstance().setSelectedSong(this.songData);
        MusicAppApplication.getInstance().showPlayerPage();
        MusicAppApplication.getInstance().getPlayerController().playAllSongs(SongManager.getInstance().getSongList(), SongManager.getInstance().getSelectedSong());
        
    }
    @FXML
    private void openPlaylistSelection() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/playlistSelection.fxml"));
            Parent root = loader.load();

            PlaylistSelectionController controller = loader.getController();
            controller.loadPlaylists(songData); // Truyền dữ liệu bài hát hiện tại vào controller

            Stage stage = new Stage();
            stage.setTitle("Select Playlist");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleLikeClick() {
        if (songData != null) {
            // Đảo ngược trạng thái yêu thích
            songData.setFavourite(!songData.isFavourite());

            // Cập nhật cơ sở dữ liệu
            SongRepository repository = new SongRepository();
            repository.updateFavouriteStatus(songData.getId(), songData.isFavourite());

            // Cập nhật giao diện
            updateLikeButton();
        }
    }

    private void updateLikeButton() {
        if (songData.isFavourite()) {
            likeButton.setText("♥"); // Đã yêu thích
        } else {
            likeButton.setText("♡"); // Chưa yêu thích
        }
    }
    // Cập nhật giao diện khi bài hát đang phát
    public void markAsPlaying() {
        songHBox.getStyleClass().add("song-playing");
    }
  
    @FXML 
    private void removeFromPlaylist()
    {
    	SongPlaylistRepository songPlaylistRepository = new SongPlaylistRepository();
    	songPlaylistRepository.removeSongFromPlaylist(currentPlaylistId, songData.getId());
    	MusicAppApplication.getInstance().getPlaylistDetailController().loadSongsByPlaylistId(currentPlaylistId);
    }
}
