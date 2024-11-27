package com.yourapp.myfirstMusicApp.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

import com.yourapp.myfirstMusicApp.model.History;

public class HistoryRepository {

    private static final String PERSISTENCE_UNIT_NAME = "my-persistence-unit";
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    // Lưu lịch sử vào cơ sở dữ liệu
    public void save(History history) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(history);
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

    // Lấy tất cả lịch sử từ cơ sở dữ liệu
    public List<History> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT h FROM History h", History.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Tìm lịch sử theo bài hát
    public List<History> findBySongId(Long songId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT h FROM History h WHERE h.song.id = :songId", History.class)
                    .setParameter("songId", songId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Xoá lịch sử
    public void delete(History history) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            history = em.merge(history);
            em.remove(history);
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

    // Cập nhật lịch sử
    public void update(History history) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(history);
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
