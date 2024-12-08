package com.yourapp.myfirstMusicApp.model;

import jakarta.persistence.*;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

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

    public Image extractAlbumArt() {
 
        try {
            // Đọc file MP3 và metadata
            AudioFile audioFile = AudioFileIO.read(new File(filePath));
            Tag tag = audioFile.getTag();

            if (tag != null && tag.hasField(FieldKey.COVER_ART)) {
                // Lấy dữ liệu ảnh bìa
                byte[] imageData = tag.getFirstArtwork().getBinaryData();
                return new Image(new ByteArrayInputStream(imageData)); // Trả về ảnh dạng Image
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
     // Sử dụng ảnh mặc định từ resources (đường dẫn tương đối)
        return new Image(getClass().getResourceAsStream("/pic/gau.jpg")); // Đường dẫn từ classpath
    }
}
