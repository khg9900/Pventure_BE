package com.example.pventure.domain.tripFolder.service;

import com.example.pventure.domain.folder.entity.Folder;
import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.tripFolder.entity.TripFolder;
import com.example.pventure.domain.tripFolder.repository.TripFolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TripFolderServiceImpl implements TripFolderService {

    private final TripFolderRepository tripFolderRepository;

    @Transactional
    @Override
    public TripFolder createTripFolder(Trip trip,Folder folder) {
        return tripFolderRepository.save(new TripFolder(trip, folder));
    }
}
