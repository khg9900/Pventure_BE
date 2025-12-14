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

    // ------------------ CREATE ------------------
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
        User user = loadUser(userId);
        Trip trip = loadTrip(tripId);
        Folder folder = loadFolder(folderId, user);

        checkMember(user, trip);

        TripFolder tripFolder = createTripFolder(trip, folder);
        return FolderResponseDto.from(tripFolder.getFolder());
    }

    // ------------------ READ ------------------
    @Override
    public List<TripResponseDto> getTrips(Long folderId, Long userId) {
        User user = loadUser(userId);
        Folder folder = loadFolder(folderId, user);

        List<TripFolder> tripFolders = tripFolderRepository.findByFolder(folder);

        return tripFolders.stream()
                .map(TripFolder::getTrip)
                .peek(trip -> checkMember(user, trip))
                .map(this::toTripResponse)
                .toList();
    }

    // ------------------ DELETE ------------------
    @Override
    public void deleteTrip(Long folderId, Long tripId, Long userId) {
        User user = loadUser(userId);
        Folder folder = loadFolder(folderId, user);
        Trip trip = loadTripWithFolders(tripId);

        TripFolder tripFolder = tripFolderRepository.findByTripAndFolder(trip, folder)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_FOLDER_TRIP));

        checkMember(user, trip);

        // 양쪽 컬렉션에서 제거
        trip.getFolders().remove(tripFolder);
        folder.getTripFolders().remove(tripFolder);
        tripFolderRepository.delete(tripFolder);
    }

    // ------------------ COUNT ------------------
    @Override
    public Long countTrips(Folder folder) {
        return tripFolderRepository.countTrips(folder);
    }

    // ------------------ PRIVATE HELPER ------------------
    private User loadUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
    }

    private Trip loadTrip(Long tripId) {
        return tripRepository.findById(tripId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TRIP));
    }

    private Trip loadTripWithFolders(Long tripId) {
        return tripRepository.findWithFolders(tripId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TRIP));
    }

    private Folder loadFolder(Long folderId, User user) {
        return folderRepository.findByIdAndUser(folderId, user)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_FOLDER));
    }

    private void checkMember(User user, Trip trip) {
        if (!memberService.isMember(user, trip)) {
            throw new ApiException(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS);
        }
    }

    private TripResponseDto toTripResponse(Trip trip) {
        List<MemberSummaryDto> members = memberService.getMemberSummaryDtoList(trip);
        Long memberCount = memberService.countMember(trip);
        return TripResponseDto.from(trip, members, memberCount);
    }
}
