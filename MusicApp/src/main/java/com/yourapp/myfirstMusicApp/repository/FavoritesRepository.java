package com.yourapp.myfirstMusicApp.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

import com.yourapp.myfirstMusicApp.model.Favorites;
import com.yourapp.myfirstMusicApp.model.Song;

public class FavoritesRepository {

    private static final String PERSISTENCE_UNIT_NAME = "my-persistence-unit";
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    // Lưu yêu thích vào cơ sở dữ liệu
    public void save(Favorites favorite) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(favorite);
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

    // Lấy tất cả yêu thích từ cơ sở dữ liệu
    public List<Favorites> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT f FROM Favorites f", Favorites.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Tìm yêu thích theo bài hát
    public List<Favorites> findBySongId(Long songId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT f FROM Favorites f WHERE f.song.id = :songId", Favorites.class)
                    .setParameter("songId", songId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Xoá yêu thích
    public void delete(Favorites favorite) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            favorite = em.merge(favorite);
            em.remove(favorite);
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

    // Cập nhật yêu thích
    public void update(Favorites favorite) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(favorite);
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
    // Lấy tất cả các bài hát ưa thích
    public List<Song> findAllFavouriteSongs() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT f.song FROM Favorites f", Song.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Lấy 20 bài hát ưa thích mới nhất
    public List<Song> findRecentFavouriteSongs() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT f.song FROM Favorites f ORDER BY f.addedAt DESC", Song.class)
                     .setMaxResults(20)
                     .getResultList();
        } finally {
            em.close();
        }
    }
}
