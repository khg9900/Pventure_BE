package com.example.pventure.domain.trip.service;

import com.example.pventure.domain.trip.dto.request.TripRequestDto;
import com.example.pventure.domain.trip.dto.request.TripSearchRequestDto;
import com.example.pventure.domain.trip.dto.response.TripResponseDto;

import java.util.List;

public interface TripService {

    TripResponseDto createTrip(Long userId, TripRequestDto tripRequestDto, boolean includeMembers);

    List<TripResponseDto> getTrips(Long userId, TripSearchRequestDto searchRequest, boolean includeMembers);

    TripResponseDto getTrip(Long userId, Long tripId, boolean includeMembers);

    TripResponseDto updateTrip(Long userId, Long tripId, TripRequestDto tripRequestDto, boolean includeMembers);

    void deleteTrip(Long userId, Long tripId);

}
