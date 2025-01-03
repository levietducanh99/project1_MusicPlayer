package com.yourapp.myfirstMusicApp.loader;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import java.io.IOException;

import com.yourapp.myfirstMusicApp.MusicAppApplication;
import com.yourapp.myfirstMusicApp.uiComponent.CustomMenu;

public class CustomMenuLoader {

	private static VBox libraryCustomMenu;
	private static VBox playerCustomMenu;
	private static VBox historyCustomMenu;
	private static VBox overviewCustomMenu;
	private static VBox favouritesCustomMenu;

	public static VBox getLibraryMenu(MusicAppApplication app) {
	    if (libraryCustomMenu == null) {
	        libraryCustomMenu = loadMenu(app);
	    }
	    return libraryCustomMenu;
	}

	public static VBox getPlayerMenu(MusicAppApplication app) {
	    if (playerCustomMenu == null) {
	        playerCustomMenu = loadMenu(app);
	    }
	    return playerCustomMenu;
	}
	public static VBox getHistoryMenu(MusicAppApplication app) {
	    if (historyCustomMenu == null) {
	    	historyCustomMenu = loadMenu(app);
	    }
	    return historyCustomMenu;
	}
	public static VBox getFavouritesMenu(MusicAppApplication app) {
	    if (favouritesCustomMenu == null) {
	    	favouritesCustomMenu = loadMenu(app);
	    }
	    return favouritesCustomMenu;
	}
	public static VBox getOverviewMenu(MusicAppApplication app) {
	    if (overviewCustomMenu == null) {
	    	overviewCustomMenu = loadMenu(app);
	    }
	    return overviewCustomMenu;
	}

	private static VBox loadMenu(MusicAppApplication app) {
	    try {
	        FXMLLoader loader = new FXMLLoader(CustomMenuLoader.class.getResource("/fxml/CustomMenu.fxml"));
	        VBox menu = loader.load();
	        CustomMenu controller = loader.getController();
	        controller.setApp(app);
	        return menu;
	    } catch (IOException e) {
	        e.printStackTrace();
	        throw new RuntimeException("Failed to load CustomMenu FXML");
	    }
	}
}
