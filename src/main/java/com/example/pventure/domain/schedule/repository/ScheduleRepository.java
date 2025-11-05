package com.example.pventure.domain.schedule.repository;

import com.example.pventure.domain.schedule.entity.Schedule;
import com.example.pventure.domain.trip.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s FROM Schedule s JOIN FETCH s.trip t WHERE t = :trip AND s.day = :day ORDER BY s.sequence")
    List<Schedule> findByTripAndDay(@Param("trip") Trip trip, @Param("day") Integer day);

    @Modifying
    @Query("UPDATE Schedule s SET s.sequence = s.sequence + 1 " +
            "WHERE s.trip.id = :tripId AND s.day = :day AND s.sequence BETWEEN :start AND :end")
    void incrementSequence(@Param("tripId") Long tripId,
                           @Param("day") Integer day,
                           @Param("start") Integer start,
                           @Param("end") Integer end);

    @Modifying
    @Query("UPDATE Schedule s SET s.sequence = s.sequence - 1 " +
            "WHERE s.trip.id = :tripId AND s.day = :day AND s.sequence BETWEEN :start AND :end")
    void decrementSequence(@Param("tripId") Long tripId,
                           @Param("day") Integer day,
                           @Param("start") Integer start,
                           @Param("end") Integer end);

    @Query("SELECT COALESCE(MAX(s.sequence), 0) FROM Schedule s WHERE s.trip.id = :tripId AND s.day = :day")
    Integer findMaxSequence(@Param("tripId") Long tripId, @Param("day") Integer day);

    @Query("SELECT s FROM Schedule s WHERE s.trip.id = :tripId AND s.day = :day AND s.sequence = :sequence")
    Optional<Schedule> findByTripAndDayAndSequence(@Param("tripId") Long tripId, @Param("day") Integer day, @Param("sequence") Integer sequence);
}
