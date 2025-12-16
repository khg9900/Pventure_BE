package com.example.pventure.domain.trip.repository;

import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip, Long>, TripRepositoryCustom {

    @Query("""
        SELECT DISTINCT t
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

    @Query("""
        SELECT COUNT(DISTINCT t)
        FROM Trip t
        JOIN t.members m
        WHERE m.user = :user
    """)
    Long countTrip(@Param("user") User user);

    @Query("""
        SELECT DISTINCT t
        FROM Trip t
        JOIN t.members m
        WHERE m.user = :user
    """)
    List<Trip> findByUser(@Param("user") User user);

    @Query("""
        SELECT DISTINCT t
        FROM Trip t
        JOIN FETCH t.members m
        JOIN FETCH m.user u
        WHERE u = :user
    """)
    List<Trip> findByUserWithMember(@Param("user") User user);

    @Query("""
        SELECT COUNT(DISTINCT t)
        FROM Trip t
        JOIN t.members m
        WHERE m.user = :user
        AND (t.startDate IS NULL OR t.endDate IS NULL)
    """)
    Long countTripsWithNoDate(@Param("user") User user);
}
