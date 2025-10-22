package com.example.pventure.domain.tripFolder.service;

import com.example.pventure.domain.folder.entity.Folder;
import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.tripFolder.entity.TripFolder;

public interface TripFolderService {
    TripFolder createTripFolder (Trip trip,Folder folder);
}
