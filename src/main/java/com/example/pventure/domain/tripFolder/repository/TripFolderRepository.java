package com.example.pventure.domain.tripFolder.repository;

import com.example.pventure.domain.folder.entity.Folder;
import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.tripFolder.entity.TripFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TripFolderRepository extends JpaRepository<TripFolder, Long> {

    @Query("SELECT DISTINCT tf FROM TripFolder tf " +
            "LEFT JOIN FETCH tf.trip t " +
            "LEFT JOIN FETCH t.members m " +
            "LEFT JOIN FETCH m.user u " +
            "WHERE tf.folder = :folder")
    List<TripFolder> findByFolder(@Param("folder")Folder folder);

    void deleteByTripAndFolder(Trip trip, Folder folder);

    @Query("SELECT CASE WHEN COUNT(tf) > 1 THEN true ELSE false END FROM TripFolder tf WHERE tf.trip = :trip")
    boolean existsMoreThanOneByTrip(@Param("trip") Trip trip);

}
