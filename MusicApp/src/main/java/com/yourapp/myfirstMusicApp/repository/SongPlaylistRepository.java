package com.yourapp.myfirstMusicApp.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDateTime;
import java.util.List;

import com.yourapp.myfirstMusicApp.model.SongPlaylist;

public class SongPlaylistRepository {

    private static final String PERSISTENCE_UNIT_NAME = "my-persistence-unit";
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    // Lưu bài hát vào playlist
    public void save(SongPlaylist playlistSong) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(playlistSong);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Lấy tất cả bài hát trong playlist
    public List<SongPlaylist> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT ps FROM SongPlaylist ps", SongPlaylist.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Tìm bài hát trong playlist theo playlist ID
    public List<SongPlaylist> findByPlaylistId(Long playlistId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT ps FROM SongPlaylist ps WHERE ps.playlist.id = :playlistId", SongPlaylist.class)
                    .setParameter("playlistId", playlistId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Xoá bài hát khỏi playlist
    public void delete(SongPlaylist playlistSong) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            playlistSong = em.merge(playlistSong);
            em.remove(playlistSong);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Cập nhật bài hát trong playlist
    public void update(SongPlaylist playlistSong) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(playlistSong);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    public void addSongToPlaylist(Long playlistId, Long songId) {
        EntityManager em = emf.createEntityManager();
        try {
            System.out.println("Start transaction...");

            em.getTransaction().begin();
            System.out.println("Transaction started.");

            // Tìm playlist và bài hát trong cơ sở dữ liệu
            var playlist = em.find(com.yourapp.myfirstMusicApp.model.Playlist.class, playlistId);
            var song = em.find(com.yourapp.myfirstMusicApp.model.Song.class, songId);

            // Kiểm tra playlist và song có null không
            if (playlist == null) {
                System.out.println("Playlist not found! ID: " + playlistId);
            } else {
                System.out.println("Playlist found: " + playlist.getName());
            }

            if (song == null) {
                System.out.println("Song not found! ID: " + songId);
            } else {
                System.out.println("Song found: " + song.getTitle());
            }

            if (playlist != null && song != null) {
                // Kiểm tra xem bài hát đã có trong playlist chưa
                var query = em.createQuery(
                        "SELECT ps FROM SongPlaylist ps WHERE ps.playlist.id = :playlistId AND ps.song.id = :songId", SongPlaylist.class);
                query.setParameter("playlistId", playlistId);
                query.setParameter("songId", songId);

                // Log số lượng kết quả tìm được
                var resultList = query.getResultList();
                System.out.println("Query result size: " + resultList.size());

                if (resultList.isEmpty()) {
                    // Nếu chưa, thêm bài hát vào playlist
                    System.out.println("Song not found in playlist, adding...");
                    SongPlaylist playlistSong = new SongPlaylist(playlist, song, LocalDateTime.now());
                    em.persist(playlistSong);
                    em.flush();  // Đảm bảo thay đổi được ghi vào cơ sở dữ liệu ngay lập tức
                    System.out.println("Song added to playlist: " + playlist.getName() + " - " + song.getTitle());
                } else {
                    System.out.println("Song is already in the playlist!");
                }
            }

            em.getTransaction().commit();
            System.out.println("Transaction committed.");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                System.out.println("Transaction rolled back.");
            }
            e.printStackTrace();
        } finally {
            em.close();
            System.out.println("EntityManager closed.");
        }
    }

    
}
