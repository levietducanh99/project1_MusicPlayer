package com.yourapp.myfirstMusicApp;

import com.yourapp.myfirstMusicApp.controller.FavouritesController;
import com.yourapp.myfirstMusicApp.controller.HistoryController;
import com.yourapp.myfirstMusicApp.controller.LibraryController;
import com.yourapp.myfirstMusicApp.controller.OverviewController;
import com.yourapp.myfirstMusicApp.controller.PlayerController;
import com.yourapp.myfirstMusicApp.model.Song;
import com.yourapp.myfirstMusicApp.repository.SongRepository;
import com.yourapp.myfirstMusicApp.uiComponent.SmallPlayer;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Data;
@Data
public class MusicAppApplication extends Application {
	 private static MusicAppApplication instance;

    private Stage primaryStage;
    private Scene smallScene;
    private Scene playerScene;
    private Scene libraryScene;
    private Scene overviewScene;
    private Scene customScene;
    private Scene favouritesScene;
    private Scene historyScene;
    private PlayerController playerController;  // Lưu playerController
    private SmallPlayer smallPlayer;
    private ObservableList<Song> playlist = FXCollections.observableArrayList(); // Danh sách bài hát
    private int currentSongIndex = 0; // Chỉ số bài hát hiện tại
    public MusicAppApplication() {
        instance = this; // Lưu instance hiện tại khi khởi tạo
    }

    public static MusicAppApplication getInstance() {
        return instance; // Trả về instance hiện tại
    }

    @Override
    public void init() {
        // Gán danh sách phát từ repository
        try {
            playlist.setAll(com.yourapp.myfirstMusicApp.repository.SongRepository.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            showErrorDialog("Lỗi", "Không thể tải danh sách bài hát.");
        }
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        
     
        
        
        // Tải trang FXML cho Player
        FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("/fxml/player.fxml"));
        AnchorPane playerPage = homeLoader.load();
        playerScene = new Scene(playerPage);
        // Lấy controller PlayerController
        playerController = homeLoader.getController();
        // Lấy instance của PlayerController (không cần tạo lại controller)
   //     playerController = PlayerController.getInstance(); // Lấy instance duy nhất
        playerController.setApp(this);

        // Truyền tham chiếu primaryStage vào controller HomeController
 
      

        // Tải trang FXML cho Library
        FXMLLoader libraryLoader = new FXMLLoader(getClass().getResource("/fxml/library.fxml"));
        BorderPane libraryPage = libraryLoader.load();
        libraryScene = new Scene(libraryPage);

        // Truyền tham chiếu primaryStage vào controller LibraryController
        LibraryController libraryController = libraryLoader.getController();
        libraryController.setApp(this);
        
        // cho smallplayer
        FXMLLoader smallLoader = new FXMLLoader(getClass().getResource("/fxml/smallPlayer.fxml"));
        HBox smallPage = smallLoader.load();
        smallScene = new Scene(smallPage);

        // Truyền tham chiếu primaryStage vào controller LibraryController
        SmallPlayer smallPlayer = smallLoader.getController();
       smallPlayer.setApp(this);
        
        
    // Tải trang FXML cho overview
       FXMLLoader overviewLoader = new FXMLLoader(getClass().getResource("/fxml/overview.fxml"));
       BorderPane overviewPage = overviewLoader.load();
       overviewScene = new Scene(overviewPage);

       // Truyền tham chiếu primaryStage vào controller LibraryController
       OverviewController overviewController = overviewLoader.getController();
       overviewController.setApp(this);     
     
     
       // Tải trang FXML cho favourite
       FXMLLoader favouritesLoader = new FXMLLoader(getClass().getResource("/fxml/favourites.fxml"));
       BorderPane favouritesPage = favouritesLoader.load();
       favouritesScene = new Scene(favouritesPage);

       // Truyền tham chiếu primaryStage vào controller LibraryController
       FavouritesController favouritesController = favouritesLoader.getController();
       favouritesController.setApp(this); 
       
    // Tải trang FXML cho History
       FXMLLoader historyLoader = new FXMLLoader(getClass().getResource("/fxml/history.fxml"));
       BorderPane historyPage = historyLoader.load();
       historyScene = new Scene(historyPage);

       // Truyền tham chiếu primaryStage vào controller LibraryController
       HistoryController historyController = historyLoader.getController();
       historyController.setApp(this);
       
       
        // Đặt Scene mặc định là Home
        primaryStage.setTitle("Music App");
        primaryStage.setScene(historyScene);
        
        primaryStage.show();
        
        
        
        
        
        
        
        
    }
// báo lỗi
    public void showErrorDialog(String title, String message) {
        // Tạo đối tượng Alert để hiển thị thông báo lỗi
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);  // Tiêu đề của thông báo
        alert.setHeaderText(null);  // Tiêu đề phụ, không cần thiết trong trường hợp này
        alert.setContentText(message);  // Nội dung lỗi sẽ hiển thị

        // Hiển thị thông báo lỗi
        alert.showAndWait();
    }
    // Chuyển đến trang Home
    public void showPlayerPage() {
        primaryStage.setScene(playerScene);
    }
    public void showOverviewPage() {
        primaryStage.setScene(overviewScene);
    }

    // Chuyển đến trang Library
    public void showLibraryPage() {
        primaryStage.setScene(libraryScene);
    }
    // Chuyển đến trang Favourite
    public void showFavouritesPage() {
        primaryStage.setScene(favouritesScene);
    }
    // Chuyển đến trang Library
    public void showHistoryPage() {
        primaryStage.setScene(historyScene);
    }


    
    // Kiểm tra nếu có bài hát đang phát
    public boolean isSongPlaying() {
        return AudioPlayer.getInstance().isPlaying();
    }

    // Dừng bài hát đang phát
    public void stopCurrentSong() {
    	 AudioPlayer.getInstance().stop();
    }

    public void showPlayerAndPlay(Song song) {
        try {
            // Nếu không có bài hát được chỉ định, phát bài hát đầu tiên trong danh sách
            if (song == null && !playlist.isEmpty()) {
                song = playlist.get(currentSongIndex);
            }

            // Kiểm tra xem bài hát có hợp lệ không
            if (song == null || song.getFilePath() == null) {
                showErrorDialog("Lỗi", "Không có bài hát hợp lệ để phát.");
                return;
            }

            // Nếu có bài hát đang phát, dừng nó
            if (isSongPlaying()) {
                stopCurrentSong();
            }

            // Chuyển sang giao diện Player
            primaryStage.setScene(playerScene);
            System.out.println("Danh sách bài hát: " + playlist);
            System.out.println("Bài hát được chọn: " + song);

            // Phát bài hát
       //    playerController.handlePlaySongFromFile(song.getFilePath());
        //    playerController.handlePlaySongFromEntity(song);
         playerController.playAllSongs(playlist, song);

            // Lắng nghe khi bài hát kết thúc để phát bài tiếp theo
        //    playerController.getAudioPlayer().setOnEndOfMedia(() -> {
        //        playNextSong();
        //    });

        } catch (Exception e) {
            e.printStackTrace();
            showErrorDialog("Lỗi khi phát nhạc", "Không thể phát bài hát mới.");
        }
    }

    // Phương thức để phát bài hát tiếp theo
    private void playNextSong() {
        if (!playlist.isEmpty()) {
            currentSongIndex = (currentSongIndex + 1) % playlist.size(); // Phát lặp vòng nếu hết danh sách
            Song nextSong = playlist.get(currentSongIndex);
            showPlayerAndPlay(nextSong);
        }
    }
    

    public static void main(String[] args) {
        launch(args);
    }
}
