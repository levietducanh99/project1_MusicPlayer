package com.yourapp.MusicApp.service;

import com.yourapp.MusicApp.model.Song;
import com.yourapp.MusicApp.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SongService {

    private final SongRepository songRepository;

    @Autowired
    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public void addSongFromFile(String title, String artist, String filePath, long duration) {
        Song song = new Song();
        song.setTitle(title);
        song.setArtist(artist);
        song.setFilePath(filePath);
        song.setDuration(duration);  // Đảm bảo có trường duration trong model Song

        songRepository.save(song);
    }
}
