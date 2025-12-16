package com.example.pventure.domain.tripFolder.controller;

import com.example.pventure.domain.folder.dto.response.FolderResponseDto;
import com.example.pventure.domain.trip.dto.response.TripResponseDto;
import com.example.pventure.domain.tripFolder.docs.AddTripToFolderDocs;
import com.example.pventure.domain.tripFolder.docs.DeleteTripInFolderDocs;
import com.example.pventure.domain.tripFolder.docs.GetTripsInFolderDocs;
import com.example.pventure.domain.tripFolder.service.TripFolderService;
import com.example.pventure.global.response.CustomResponse;
import com.example.pventure.global.response.CustomResponseHelper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "FolderTrip", description = "폴더-여행 관련 API")
@RestController
@RequestMapping("/folders/{folderId}/trips")
@RequiredArgsConstructor
public class FolderTripController {

    private final TripFolderService tripFolderService;

    @AddTripToFolderDocs
    @PostMapping
    public ResponseEntity<CustomResponse<FolderResponseDto>> addTrip(
            @RequestParam Long userId,
            @PathVariable Long folderId,
            @RequestParam Long tripId) {
        return CustomResponseHelper.ok(tripFolderService.addTrip(folderId, tripId, userId));
    }

    @GetTripsInFolderDocs
    @GetMapping
    public ResponseEntity<CustomResponse<List<TripResponseDto>>> getTrips(
            @RequestParam Long userId,
            @PathVariable Long folderId) {
        return CustomResponseHelper.ok(tripFolderService.getTrips(folderId, userId));
    }

    @DeleteTripInFolderDocs
    @DeleteMapping("/{tripId}")
    public ResponseEntity<Void> deleteTrip(
            @RequestParam Long userId,
            @PathVariable Long folderId,
            @PathVariable Long tripId) {
        tripFolderService.deleteTrip(folderId, tripId, userId);
        return CustomResponseHelper.noContent();
    }
}
