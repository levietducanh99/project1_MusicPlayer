package com.yourapp.myfirstMusicApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import com.yourapp.myfirstMusicApp.repository.HistoryRepository;
import com.yourapp.myfirstMusicApp.repository.SongRepository;

@Entity
@Getter
@Setter
@Table(name = "history")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relationship with Song (N-1)
    @ManyToOne
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    @Column(name = "played_at", nullable = false)
    private LocalDateTime playedAt;
    @Column(name = "song_name",nullable = false)
    private String songName;

    // Constructors
    public History() {
    }

    public History(Song asong, LocalDateTime playedAt,String songName) {
        this.song = asong;
        this.playedAt = playedAt;
        this.songName = asong.getTitle();
    }
 // Phương thức tĩnh để lưu lịch sử phát nhạc
    public static void savePlayHistory(String filePath) {
        // Tìm bài hát từ filePath
        SongRepository songRepository = new SongRepository();
        Song song = songRepository.findByFilePath(filePath).stream().findFirst().orElse(null);

        if (song != null) {
            // Tạo đối tượng History mới
            History history = new History();
            history.setSong(song); // Liên kết bài hát với lịch sử
            history.setPlayedAt(LocalDateTime.now()); // Lưu thời gian phát nhạc
            history.setSongName(song.getTitle());
            // Lưu lịch sử vào cơ sở dữ liệu
            HistoryRepository historyRepository = new HistoryRepository();
            historyRepository.save(history); // Lưu đối tượng history vào cơ sở dữ liệu
        } else {
            System.out.println("Bài hát không tìm thấy.");
        }
    }
}

