package com.yourapp.myfirstMusicApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "artist")
    private String artist;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "duration")
    private long duration;

    @Column(name = "album")
    private String album;

    // Relationship with Favorites (1-1)
    @OneToOne(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
    private Favorites favorite;

    // Relationship with History (1-N)
    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<History> histories;

    // Relationship with PlaylistSongs (1-N)
    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaylistSongs> playlistSongs;

    // Constructors
    public Song() {
    }

    public Song(String title, String artist, String filePath, long duration) {
        this.title = title;
        this.artist = artist;
        this.filePath = filePath;
        this.duration = duration;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;  // Trường hợp so sánh với chính nó
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;  // Trường hợp null hoặc khác lớp
        }

        Song otherSong = (Song) obj;
        return this.id == otherSong.id;  // So sánh theo id
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);  // Sử dụng Long.hashCode() nếu id là Long
    }


}
