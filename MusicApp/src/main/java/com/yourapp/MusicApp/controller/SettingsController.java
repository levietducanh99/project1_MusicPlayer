package com.yourapp.MusicApp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

import com.yourapp.MusicApp.MusicAppApplication;

public class SettingsController {

    private MusicAppApplication app;

    @FXML
    private void handleGoBackToHome(ActionEvent event) {
        app.showHomePage(); // Quay lại trang Home
    }

    // Thiết lập đối tượng MusicAppApplication cho controller
    public void setApp(MusicAppApplication app) {
        this.app = app;
    }
}
