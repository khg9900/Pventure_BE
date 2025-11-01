package com.example.pventure.domain.tripFolder.controller;

import com.example.pventure.domain.folder.dto.response.FolderResponseDto;
import com.example.pventure.domain.trip.dto.response.TripResponseDto;
import com.example.pventure.domain.tripFolder.service.TripFolderService;
import com.example.pventure.global.response.CustomResponse;
import com.example.pventure.global.response.CustomResponseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/folders/{folderId}/trips")
@RequiredArgsConstructor
public class FolderTripController {

    private final TripFolderService tripFolderService;
    // 폴더에 여행 추가
    @PostMapping
    public ResponseEntity<CustomResponse<FolderResponseDto>> addTrip(
            @RequestParam Long userId,
            @PathVariable Long folderId,
            @RequestParam Long tripId) {

        return CustomResponseHelper.ok(tripFolderService.addTrip(folderId, tripId, userId));
    }

    // 폴더 내 여행 조회
    @GetMapping
    public ResponseEntity<CustomResponse<List<TripResponseDto>>> getTrips(
            @RequestParam Long userId,
            @PathVariable Long folderId) {
        return CustomResponseHelper.ok(tripFolderService.getTrips(folderId, userId));
    }

    // 폴더에서 여행 삭제
    @DeleteMapping("/{tripId}")
    public ResponseEntity<Void> deleteTrip(
            @RequestParam Long userId,
            @PathVariable Long folderId,
            @PathVariable Long tripId) {
        tripFolderService.deleteTrip(folderId, tripId, userId);
        return CustomResponseHelper.noContent();
    }
}
