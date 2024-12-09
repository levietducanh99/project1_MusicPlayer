package com.yourapp.myfirstMusicApp.uiComponent;

import com.yourapp.myfirstMusicApp.controller.PlaylistController2;
import com.yourapp.myfirstMusicApp.model.Playlist;
import com.yourapp.myfirstMusicApp.model.Song;
import com.yourapp.myfirstMusicApp.repository.PlaylistRepository;
import com.yourapp.myfirstMusicApp.repository.SongPlaylistRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class PlaylistSelectionController {

    @FXML
    private TilePane playlistTilePane;

    @FXML
    private Button closeButton;
   

    private Song currentSong;

    public void setSong(Song song) {
        this.currentSong = song;
      
    }

    @FXML
    private void initialize() {
        closeButton.setOnAction(event -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }

    public void loadPlaylists(Song currentSong) {
        PlaylistRepository playlistRepository = new PlaylistRepository();
        List<Playlist> playlists = playlistRepository.findAll();

        for (Playlist playlist : playlists) {
            // Tạo một VBox cho từng playlist
            VBox playlistBox = createPlaylistBox(playlist, currentSong);

            // Thêm vào TilePane
            playlistTilePane.getChildren().add(playlistBox);
        }
    }

    private VBox createPlaylistBox(Playlist playlist, Song currentSong) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/playlistV2.fxml"));
            VBox playlistBox = loader.load();

            PlaylistController2 controller = loader.getController();
            controller.setPlaylistSelectionController(this);
            controller.setPlaylistData(playlist, currentSong); // Truyền dữ liệu bài hát hiện tại

            return playlistBox;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void addToPlaylist(Playlist playlist) {
        if (currentSong != null) {
            SongPlaylistRepository repository = new SongPlaylistRepository();
            repository.addSongToPlaylist(playlist.getId(), currentSong.getId());

            System.out.println("Added " + currentSong.getTitle() + " to playlist: " + playlist.getName());
        } else {
            System.out.println("Song is null, cannot add to playlist.");
        }
    }

}
