package com.yourapp.myfirstMusicApp.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

import com.yourapp.myfirstMusicApp.model.PlaylistSongs;

public class PlaylistSongsRepository {

    private static final String PERSISTENCE_UNIT_NAME = "my-persistence-unit";
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    // Lưu bài hát vào playlist
    public void save(PlaylistSongs playlistSong) {
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
    public List<PlaylistSongs> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT ps FROM PlaylistSongs ps", PlaylistSongs.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Tìm bài hát trong playlist theo playlist ID
    public List<PlaylistSongs> findByPlaylistId(Long playlistId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT ps FROM PlaylistSongs ps WHERE ps.playlist.id = :playlistId", PlaylistSongs.class)
                    .setParameter("playlistId", playlistId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Xoá bài hát khỏi playlist
    public void delete(PlaylistSongs playlistSong) {
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
    public void update(PlaylistSongs playlistSong) {
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
}
