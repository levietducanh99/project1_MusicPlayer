package com.yourapp.myfirstMusicApp.controller;

import com.yourapp.myfirstMusicApp.uiComponent.CustomMenu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private BorderPane rootPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	 System.out.println("Initializing MainController...");

         // Khởi tạo CustomMenu và thêm vào mainContainer
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CustomMenu.fxml"));
         try {
             // Load FXML của CustomMenu
             VBox customMenu = loader.load();
             
             // Lấy controller của CustomMenu để thiết lập các hành động
             CustomMenu controller = loader.getController();
           
             
             // Thêm CustomMenu vào VBox (mainContainer)
             rootPane.getChildren().add(customMenu);
             System.out.println("CustomMenu has been added.");
             
         } catch (IOException e) {
             e.printStackTrace();
         }
    }
}
