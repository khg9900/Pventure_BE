package com.example.pventure.domain.album.repository;

import com.example.pventure.domain.album.entity.Album;
import com.example.pventure.domain.trip.entity.Trip;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long>, AlbumRepositoryCustom {

    Optional<Album> findByIdAndTrip(Long id, Trip trip);
}
