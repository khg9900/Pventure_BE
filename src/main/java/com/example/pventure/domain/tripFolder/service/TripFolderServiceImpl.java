package com.example.pventure.domain.tripFolder.service;

import com.example.pventure.domain.folder.dto.response.FolderResponseDto;
import com.example.pventure.domain.folder.entity.Folder;
import com.example.pventure.domain.folder.repository.FolderRepository;
import com.example.pventure.domain.member.dto.response.MemberSummaryDto;
import com.example.pventure.domain.member.service.MemberService;
import com.example.pventure.domain.trip.dto.response.TripResponseDto;
import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.trip.repository.TripRepository;
import com.example.pventure.domain.tripFolder.entity.TripFolder;
import com.example.pventure.domain.tripFolder.repository.TripFolderRepository;
import com.example.pventure.domain.user.entity.User;
import com.example.pventure.domain.user.repository.UserRepository;
import com.example.pventure.global.exception.ApiException;
import com.example.pventure.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TripFolderServiceImpl implements TripFolderService {

    private final TripFolderRepository tripFolderRepository;
    private final UserRepository userRepository;
    private final FolderRepository folderRepository;
    private final TripRepository tripRepository;
    private final MemberService memberService;

    @Override
    public TripFolder createTripFolder(Trip trip, Folder folder) {
        try {
            return tripFolderRepository.save(new TripFolder(trip, folder));
        } catch (DataIntegrityViolationException e) {

            throw new ApiException(ErrorCode.DUPLICATE_FOLDER_TRIP);
        }
    }

    @Override
    public FolderResponseDto addTrip(Long folderId, Long tripId, Long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
        Trip trip = tripRepository.findById(tripId).
                orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TRIP));
        Folder folder = folderRepository.findByIdAndUser(folderId, user).
                orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_FOLDER));

        memberService.isMember(user, trip);

        TripFolder tripFolder=createTripFolder(trip,folder);

        return FolderResponseDto.from(tripFolder.getFolder());
    }

    @Override
    public List<TripResponseDto> getTrips(Long folderId, Long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
        Folder folder = folderRepository.findByIdAndUser(folderId, user).
                orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_FOLDER));

        List<TripFolder> tripFolders =tripFolderRepository.findByFolder(folder);

        return tripFolders.stream()
                .map(TripFolder::getTrip)
                .map(trip -> {
                    List<MemberSummaryDto> members = memberService.getMemberSummaryDtoList(trip);
                    return TripResponseDto.from(trip, members);
                })
                .toList();
    }

    @Override
    public void deleteTrip(Long folderId, Long tripId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TRIP));
        Folder folder = folderRepository.findByIdAndUser(folderId, user)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_FOLDER));

        boolean hasMultipleTrips = tripFolderRepository.existsMoreThanOneByTrip(trip);

        tripFolderRepository.deleteByTripAndFolder(trip, folder);

        if (folder.isDefault() && !hasMultipleTrips) {
            throw new ApiException(ErrorCode.TRIP_MUST_BELONG_TO_AT_LEAST_ONE_FOLDER);
        }

        if (!folder.isDefault() && !hasMultipleTrips) {
            Folder defaultFolder = folderRepository.findDefaultFolderByUser(user)
                    .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_FOLDER));
            tripFolderRepository.save(new TripFolder(trip, defaultFolder));
        }

    }
}
