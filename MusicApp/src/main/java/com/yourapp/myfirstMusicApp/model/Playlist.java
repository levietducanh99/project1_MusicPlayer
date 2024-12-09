package com.yourapp.myfirstMusicApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "playlists")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "total_song")
    private int totalSong;

    @Column(name = "total_length")
    private long totalLength;

    // Relationship with PlaylistSongs (1-N)
    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SongPlaylist> playlistSongs = new ArrayList<>();

    // Constructors
    public Playlist() {
    }

    public Playlist(String name, LocalDateTime createdAt, int totalSong, long totalLength) {
        this.name = name;
        this.createdAt = createdAt;
        this.totalSong = totalSong;
        this.totalLength = totalLength;
    }
}
