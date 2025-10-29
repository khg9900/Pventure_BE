package com.example.pventure.domain.tripFolder.service;

import com.example.pventure.domain.folder.entity.Folder;
import com.example.pventure.domain.trip.entity.Trip;

public interface TripFolderService {
    void createTripFolder (Trip trip,Folder folder);
}
