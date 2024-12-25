package com.yourapp.myfirstMusicApp.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import java.util.stream.Collectors;

import com.yourapp.myfirstMusicApp.model.History;
import com.yourapp.myfirstMusicApp.model.Song;

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
 // Lấy tất cả bài hát từ lịch sử
    public List<Song> findAllSong() {
        EntityManager em = emf.createEntityManager();
        try {
            // Lấy danh sách tất cả các lịch sử
            List<History> histories = em.createQuery("SELECT h FROM History h ORDER BY h.playedAt DESC", History.class).getResultList();
            // Lấy danh sách bài hát từ lịch sử
            return histories.stream()
                    .map(History::getSong) // Lấy bài hát từ mỗi lịch sử
                    .distinct()           // Loại bỏ bài hát trùng lặp
                    .collect(Collectors.toList());
        } finally {
            em.close();
        }
    }
 // Lấy 20 bài hát phát gần nhất từ lịch sử
    public List<Song> findRecentSongs() {
        EntityManager em = emf.createEntityManager();
        try {
            // Lấy danh sách lịch sử, sắp xếp theo thời gian phát gần nhất và giới hạn 20 bài hát
            List<History> histories = em.createQuery(
                    "SELECT h FROM History h ORDER BY h.playedAt DESC", History.class)
                    .setMaxResults(20) // Giới hạn số lượng kết quả
                    .getResultList();

            // Lấy danh sách bài hát từ lịch sử và loại bỏ trùng lặp
            return histories.stream()
                    .map(History::getSong) // Lấy bài hát từ mỗi lịch sử
                    .distinct()           // Loại bỏ bài hát trùng lặp
                    .collect(Collectors.toList());
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
