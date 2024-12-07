package com.yourapp.myfirstMusicApp.uiComponent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import lombok.Data;

import com.yourapp.myfirstMusicApp.MusicAppApplication;

import java.io.IOException;
@Data
public class CustomMenu extends VBox {
	
    private MusicAppApplication app; // Để truy cập vào các trang trong ứng dụng

    @FXML
    private Button goToLibraryButton;

    @FXML
    private Button goToCustomButton;


    @FXML
    private Button goToPlayerButton;
    VBox customMenu ;
    // Constructor mặc định
    public CustomMenu() {
        System.out.println("CustomMenu created with default constructor.");
    }
    // Constructor để load FXML và thiết lập hành động
    public CustomMenu(MusicAppApplication app) {
    	System.out.println("Loading CustomMenu...");

        this.app = app;
        
        // Tự động load FXML khi khởi tạo CustomMenu
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CustomMenu.fxml"));
     
       
        try {
        	customMenu =   loader.load(); // Load FXML mà không cần setRoot
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Xử lý sự kiện khi nhấn vào nút "Go to Library"
    @FXML
    public void handleGoToLibraryButton(ActionEvent event) {
        // Chuyển màn hình đến Library
        app.showLibraryPage(); // Gọi phương thức chuyển đến thư viện
    }

    // Xử lý sự kiện khi nhấn vào nút "Go to Player"
    @FXML
    public void handleGoToCustomButton(ActionEvent event) {
        // Chuyển màn hình đến Player
        app.showCustomPage(); // Gọi phương thức chuyển đến Player
    }
    public void handleGoToPlayerButton(ActionEvent event) {
        // Chuyển màn hình đến Player
        app.showPlayerPage(); // Gọi phương thức chuyển đến Player
    }
    public VBox getRoot() {
        return this;
    } 
    public void setApp(MusicAppApplication app) {
        this.app = app;
    }
}

