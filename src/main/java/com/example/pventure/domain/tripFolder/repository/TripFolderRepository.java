package com.example.pventure.domain.tripFolder.repository;

import com.example.pventure.domain.folder.entity.Folder;
import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.tripFolder.entity.TripFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TripFolderRepository extends JpaRepository<TripFolder, Long> {

    @Query("SELECT DISTINCT tf FROM TripFolder tf " +
            "LEFT JOIN FETCH tf.trip t " +
            "LEFT JOIN FETCH t.members m " +
            "LEFT JOIN FETCH m.user u " +
            "WHERE tf.folder = :folder")
    List<TripFolder> findByFolder(@Param("folder")Folder folder);

    Optional<TripFolder> findByTripAndFolder(Trip trip, Folder folder);
}
