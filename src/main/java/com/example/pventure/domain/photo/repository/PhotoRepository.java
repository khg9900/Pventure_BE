package com.example.pventure.domain.photo.repository;

import com.example.pventure.domain.album.entity.Album;
import com.example.pventure.domain.photo.entity.Photo;
import com.example.pventure.domain.trip.entity.Trip;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    List<Photo> findAllByAlbum(Album album);

    List<Photo> findAllByTripAndAlbumIsNull(Trip trip);

    List<Photo> findAllByIdIn(List<Long> id);

    List<Photo> findAllByIdInAndTrip(List<Long> id, Trip trip);

    Optional<Photo> findByIdAndTrip(Long id, Trip trip);

    @Query("SELECT COUNT(*) FROM Photo p WHERE p.album = :album")
    Long countByAlbum(@Param("album") Album album);
}
