package com.example.pventure.domain.trip.controller;

import com.example.pventure.domain.trip.dto.request.TripRequestDto;
import com.example.pventure.domain.trip.dto.response.TripResponseDto;
import com.example.pventure.domain.trip.service.TripService;
import com.example.pventure.global.response.ApiResponse;
import com.example.pventure.global.response.ApiResponseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @PostMapping
    public ResponseEntity<ApiResponse<TripResponseDto>> createTrip(
            @RequestParam Long userId,
            @RequestBody TripRequestDto tripRequestDto
    ) {
        TripResponseDto response = tripService.createTrip(userId, tripRequestDto);
        return ApiResponseHelper.created(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TripResponseDto>>> getTrips(@RequestParam Long userId) {
        List<TripResponseDto> trips = tripService.getTrips(userId);
        return ApiResponseHelper.ok(trips);
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<ApiResponse<TripResponseDto>> getTrip(@PathVariable Long tripId) {
        TripResponseDto response = tripService.getTrip(tripId);
        return ApiResponseHelper.ok(response);
    }

    @PutMapping("/{tripId}")
    public ResponseEntity<ApiResponse<TripResponseDto>> updateTrip(
            @RequestParam Long userId,
            @PathVariable Long tripId,
            @RequestBody TripRequestDto tripRequestDto
    ) {
        TripResponseDto response = tripService.updateTrip(userId, tripId, tripRequestDto);
        return ApiResponseHelper.ok(response);
    }


    @DeleteMapping("/{tripId}")
    public ResponseEntity<Void> deleteTrip(
            @RequestParam Long userId,
            @PathVariable Long tripId
    ) {
        tripService.deleteTrip(userId, tripId);
        return ApiResponseHelper.noContent();
    }
}