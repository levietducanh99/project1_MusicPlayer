package com.yourapp.myfirstMusicApp.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

import com.yourapp.myfirstMusicApp.model.Playlists;

public class PlaylistsRepository {

    private static final String PERSISTENCE_UNIT_NAME = "my-persistence-unit";
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    // Lưu playlist vào cơ sở dữ liệu
    public void save(Playlists playlist) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(playlist);
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

    // Lấy tất cả playlist từ cơ sở dữ liệu
    public List<Playlists> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Playlists p", Playlists.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Tìm playlist theo tên
    public List<Playlists> findByName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Playlists p WHERE p.name = :name", Playlists.class)
                    .setParameter("name", name)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Xoá playlist
    public void delete(Playlists playlist) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            playlist = em.merge(playlist);
            em.remove(playlist);
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

    // Cập nhật playlist
    public void update(Playlists playlist) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(playlist);
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
