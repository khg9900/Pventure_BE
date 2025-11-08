package com.example.pventure.domain.folder.repository;

import com.example.pventure.domain.folder.entity.Folder;
import com.example.pventure.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Long> {

    @Query("SELECT f FROM Folder f WHERE f.user = :user AND f.isDefault = true")
    Optional<Folder> findDefaultByUser(@Param("user") User user);

    @Query("SELECT DISTINCT f FROM Folder f " +
            "LEFT JOIN FETCH f.tripFolders tf " +
            "LEFT JOIN FETCH tf.trip t " +
            "WHERE f.user = :user")
    List<Folder> findAllByUser(@Param("user") User user);

    @Query("SELECT f FROM Folder f WHERE f.id = :id AND f.user = :user")
    Optional<Folder> findByIdAndUser(@Param("id") Long id, @Param("user") User user);

    @Query("""
        SELECT DISTINCT f
        FROM Folder f
        LEFT JOIN FETCH f.tripFolders tf
        LEFT JOIN FETCH tf.trip
        WHERE f.user = :user AND f.id = :id
    """)
    Optional<Folder> findWithTrips(@Param("id") Long id, @Param("user") User user);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Folder f WHERE f.user = :user AND f.isDefault = true")
    boolean hasDefault(@Param("user") User user);
}