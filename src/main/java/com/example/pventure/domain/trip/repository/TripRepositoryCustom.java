package com.example.pventure.domain.trip.repository;

import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.user.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface TripRepositoryCustom {

    List<Trip> findByUserAndDateRange(User user, LocalDate startDate, LocalDate endDate);
}
