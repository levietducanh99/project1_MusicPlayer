package com.yourapp.myfirstMusicApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "playlist_songs")
public class SongPlaylist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relationship with Playlists (N-1)
    @ManyToOne
    @JoinColumn(name = "playlist_id", nullable = false)
    private Playlist playlist;

    // Relationship with Songs (N-1)
    @ManyToOne
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    @Column(name = "added_at", nullable = false)
    private LocalDateTime addedAt;

    // Constructors
    public SongPlaylist() {
    }

    public SongPlaylist(Playlist playlist, Song song, LocalDateTime addedAt) {
        this.playlist = playlist;
        this.song = song;
        this.addedAt = addedAt;
    }
}
