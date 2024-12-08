package com.yourapp.myfirstMusicApp;

import java.util.ArrayList;
import java.util.List;

import com.yourapp.myfirstMusicApp.model.Song;

import lombok.Data;
@Data
public class SongManager {
    private static SongManager instance;
    private List<Song> songList;
    private Song selectedSong;

    private SongManager() {
        songList = new ArrayList<>();
    }

    public static SongManager getInstance() {
        if (instance == null) {
            instance = new SongManager();
        }
        return instance;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songs) {
        this.songList = songs;
    }

    public void addSong(Song song) {
        songList.add(song);
    }

    public void removeSong(Song song) {
        songList.remove(song);
    }
}
