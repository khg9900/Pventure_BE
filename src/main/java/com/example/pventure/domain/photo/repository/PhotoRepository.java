package com.example.pventure.domain.photo.repository;

import com.example.pventure.domain.album.entity.Album;
import com.example.pventure.domain.photo.entity.Photo;
import com.example.pventure.domain.trip.entity.Trip;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    List<Photo> findAllByAlbum(Album album);

    List<Photo> findAllByTripAndAlbumIsNull(Trip trip);

    List<Photo> findAllByIdInAndTrip(Set<Long> id, Trip trip);

    Optional<Photo> findByIdAndTrip(Long id, Trip trip);

    Long countByAlbum(Album album);
}
