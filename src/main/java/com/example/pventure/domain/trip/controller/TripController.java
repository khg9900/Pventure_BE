package com.example.pventure.domain.trip.controller;

import com.example.pventure.domain.trip.docs.*;
import com.example.pventure.domain.trip.dto.request.TripRequestDto;
import com.example.pventure.domain.trip.dto.request.TripSearchRequestDto;
import com.example.pventure.domain.trip.dto.request.TripUpdateDto;
import com.example.pventure.domain.trip.dto.response.TripResponseDto;
import com.example.pventure.domain.trip.service.TripService;
import com.example.pventure.global.response.CustomResponse;
import com.example.pventure.global.response.CustomResponseHelper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Trip", description = "여행 관련 API")
@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @CreateTripDocs
    @PostMapping
    public ResponseEntity<CustomResponse<TripResponseDto>> createTrip(
            @RequestParam Long userId,
            @RequestBody @Valid TripRequestDto tripRequestDto,
            @RequestParam(defaultValue = "false") boolean includeMembers
    ) {
        TripResponseDto response = tripService.createTrip(userId, tripRequestDto, includeMembers);
        return CustomResponseHelper.created(response);
    }

    @GetTripsDocs
    @GetMapping
    public ResponseEntity<CustomResponse<List<TripResponseDto>>> getTrips(
            @ModelAttribute TripSearchRequestDto searchRequest,
            @RequestParam Long userId,
            @RequestParam(defaultValue = "false") boolean includeMembers
    ) {
        List<TripResponseDto> trips = tripService.getTrips(userId, searchRequest, includeMembers);
        return CustomResponseHelper.ok(trips);
    }

    @GetTripDocs
    @GetMapping("/{tripId}")
    public ResponseEntity<CustomResponse<TripResponseDto>> getTrip(
            @RequestParam Long userId,
            @PathVariable Long tripId,
            @RequestParam(defaultValue = "false") boolean includeMembers
    ) {
        TripResponseDto response = tripService.getTrip(userId, tripId, includeMembers);
        return CustomResponseHelper.ok(response);
    }

    @UpdateTripDocs
    @PutMapping("/{tripId}")
    public ResponseEntity<CustomResponse<TripResponseDto>> updateTrip(
            @RequestParam Long userId,
            @PathVariable Long tripId,
            @RequestBody @Valid TripUpdateDto tripUpdateDto,
            @RequestParam(defaultValue = "false") boolean includeMembers
    ) {
        TripResponseDto response = tripService.updateTrip(userId, tripId, tripUpdateDto, includeMembers);
        return CustomResponseHelper.ok(response);
    }

    @DeleteTripDocs
    @DeleteMapping("/{tripId}")
    public ResponseEntity<Void> deleteTrip(
            @RequestParam Long userId,
            @PathVariable Long tripId
    ) {
        tripService.deleteTrip(userId, tripId);
        return CustomResponseHelper.noContent();
    }
}
