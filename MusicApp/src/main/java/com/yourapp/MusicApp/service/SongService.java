package com.yourapp.MusicApp.service;

import com.yourapp.MusicApp.model.Song;
import com.yourapp.MusicApp.repository.SongRepository;

import java.util.List;

public class SongService {

    private SongRepository songRepository;

    public SongService() {
        this.songRepository = new SongRepository();
    }

    // Lấy tất cả bài hát
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    // Tìm bài hát theo tên
    public List<Song> getSongsByTitle(String title) {
        return songRepository.findByTitle(title);
    }

    // Tìm bài hát theo ca sĩ
    public List<Song> getSongsByArtist(String artist) {
        return songRepository.findByArtist(artist);
    }

    // Lưu bài hát
    public void saveSong(Song song) {
        songRepository.save(song);
    }

    // Xóa bài hát
    public void deleteSong(Song song) {
        songRepository.delete(song);
    }

    // Cập nhật bài hát
    public void updateSong(Song song) {
        songRepository.update(song);
    }
}
