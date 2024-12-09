package com.yourapp.myfirstMusicApp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import com.yourapp.myfirstMusicApp.model.Playlist;
import com.yourapp.myfirstMusicApp.model.Song;
import com.yourapp.myfirstMusicApp.repository.SongPlaylistRepository;
import com.yourapp.myfirstMusicApp.uiComponent.PlaylistSelectionController;

public class PlaylistController2 {

    @FXML
    private Label playlistName;
    private Song currentSong;
    private Playlist currentPlaylist;  // Thêm trường Playlist vào controller
 
    @FXML
    private Button selectPlaylist;
    @FXML
    private ImageView playlistThumbnail;
    private PlaylistSelectionController playlistSelectionController;
    public void setPlaylistSelectionController(PlaylistSelectionController controller) {
        this.playlistSelectionController = controller;
    }
    public void setPlaylistData(Playlist playlist,Song song) {
    	this.currentPlaylist = playlist;  // Lưu playlist vào controller
        playlistName.setText(playlist.getName());
        this.currentSong = song;
       
        // Add thumbnail logic if applicable
    }
    @FXML
    private void addToPlaylist() {
        if (currentSong != null && currentPlaylist != null) {
            SongPlaylistRepository repository = new SongPlaylistRepository();
            repository.addSongToPlaylist(currentPlaylist.getId(),currentSong.getId()); // Sử dụng playlist và song đã có sẵn
            showSuccessPopup(currentSong.getTitle(), currentPlaylist.getName());
            System.out.println("Added " + currentSong.getTitle() + " to playlist: " + currentPlaylist.getName());
        } else {
            System.out.println("Song or Playlist is null, cannot add to playlist.");
        }
    }
    private void showSuccessPopup(String songTitle, String playlistName) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Song Added Successfully");
        alert.setContentText("The song \"" + songTitle + "\" has been added to the playlist \"" + playlistName + "\".");

        // Optional: Customize the button to close the alert
        alert.getButtonTypes().setAll(ButtonType.OK);

        // Show the alert
        alert.showAndWait();
    }
}
