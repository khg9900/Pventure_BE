package com.example.pventure.domain.tripFolder.service;

import com.example.pventure.domain.folder.dto.response.FolderResponseDto;
import com.example.pventure.domain.folder.entity.Folder;
import com.example.pventure.domain.trip.dto.response.TripResponseDto;
import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.tripFolder.entity.TripFolder;

import java.util.List;

public interface TripFolderService {
    TripFolder createTripFolder (Trip trip, Folder folder);

    FolderResponseDto addTrip(Long folderId, Long tripId, Long userId);

    List<TripResponseDto> getTrips(Long folderId, Long userId);

    void deleteTrip(Long folderId, Long tripId, Long userId);
}
