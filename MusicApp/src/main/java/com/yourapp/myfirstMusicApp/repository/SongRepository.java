package com.yourapp.myfirstMusicApp.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

import com.yourapp.myfirstMusicApp.model.Song;

public class SongRepository {

    // Tạo EntityManagerFactory một lần duy nhất khi khởi tạo ứng dụng
    private static final String PERSISTENCE_UNIT_NAME = "my-persistence-unit";
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    // Lưu bài hát vào cơ sở dữ liệu
    public void save(Song song) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(song);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();  // Rollback transaction if there is an error
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Lấy tất cả bài hát từ cơ sở dữ liệu
    public static List<Song> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT s FROM Song s", Song.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Tìm bài hát theo tên
    public List<Song> findByTitle(String title) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT s FROM Song s WHERE s.title = :title", Song.class)
                    .setParameter("title", title)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Tìm bài hát theo ca sĩ
    public List<Song> findByArtist(String artist) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT s FROM Song s WHERE s.artist = :artist", Song.class)
                    .setParameter("artist", artist)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    
    // Xoá bài hát khỏi cơ sở dữ liệu
    public void delete(Song song) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            song = em.merge(song);  // Ensure the song is attached to the context
            em.remove(song);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();  // Rollback transaction if there is an error
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Cập nhật bài hát trong cơ sở dữ liệu
    public void update(Song song) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(song);  // Merge the entity to the current persistence context
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();  // Rollback transaction if there is an error
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
 // Tìm bài hát theo filePath
    public List<Song> findByFilePath(String filePath) {
        EntityManager em = emf.createEntityManager();
        try {
            // Tạo truy vấn để tìm bài hát theo filePath
            return em.createQuery("SELECT s FROM Song s WHERE s.filePath = :filePath", Song.class)
                    .setParameter("filePath", filePath)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    public void updateFavouriteStatus(Long songId, boolean isFavourite) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Song song = em.find(Song.class, songId);
            if (song != null) {
                song.setFavourite(isFavourite);
                em.merge(song);
            }
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
