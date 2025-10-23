package com.example.pventure.domain.trip.service;

import com.example.pventure.domain.trip.dto.request.TripRequestDto;
import com.example.pventure.domain.trip.dto.response.TripResponseDto;

import java.util.List;

public interface TripService {

    TripResponseDto createTrip(Long userId, TripRequestDto tripRequestDto);

    List<TripResponseDto> getTrips(Long userId);

    TripResponseDto getTrip(Long tripId);

    TripResponseDto updateTrip(Long userId, Long tripId, TripRequestDto tripRequestDto);

    void deleteTrip(Long userId, Long tripId);

}
