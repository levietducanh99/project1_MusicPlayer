package com.yourapp.myfirstMusicApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "favorites")
public class Favorites {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relationship with Song (1-1)
    @OneToOne
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    @Column(name = "added_at", nullable = false)
    private LocalDateTime addedAt;

    // Constructors
    public Favorites() {
    }

    public Favorites(Song song, LocalDateTime addedAt) {
        this.song = song;
        this.addedAt = addedAt;
    }
}
