package com.example.pventure.domain.trip.repository;

import com.example.pventure.domain.trip.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip, Long> ,TripRepositoryCustom {
    @Query("""
    SELECT t
    FROM Trip t
    LEFT JOIN FETCH t.folders
    WHERE t.id = :tripId
""")
    Optional<Trip> findWithFolders(@Param("tripId") Long tripId);

    @Query("""
    SELECT DISTINCT t
    FROM Trip t
    LEFT JOIN FETCH t.members m
    WHERE t.id = :tripId
""")
    Optional<Trip> findByIdWithMember(@Param("tripId") Long tripId);

    @Query("""
    SELECT DISTINCT t
    FROM Trip t
    LEFT JOIN FETCH t.members m
    LEFT JOIN FETCH m.user
    WHERE t.id = :tripId
""")
    Optional<Trip> findByIdWithMemberAndUser(@Param("tripId") Long tripId);
}
