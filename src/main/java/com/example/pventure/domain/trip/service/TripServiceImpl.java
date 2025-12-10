package com.example.pventure.domain.trip.service;

import com.example.pventure.domain.folder.entity.Folder;
import com.example.pventure.domain.folder.service.FolderService;
import com.example.pventure.domain.member.dto.response.MemberSummaryDto;
import com.example.pventure.domain.member.service.MemberService;
import com.example.pventure.domain.trip.dto.request.FolderAttachable;
import com.example.pventure.domain.trip.dto.request.TripRequestDto;
import com.example.pventure.domain.trip.dto.request.TripSearchRequestDto;
import com.example.pventure.domain.trip.dto.request.TripUpdateDto;
import com.example.pventure.domain.trip.dto.response.TripResponseDto;
import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.trip.repository.TripRepository;
import com.example.pventure.domain.trip.util.TripFinder;
import com.example.pventure.domain.tripFolder.service.TripFolderService;
import com.example.pventure.domain.user.entity.User;
import com.example.pventure.domain.user.repository.UserRepository;
import com.example.pventure.global.exception.ApiException;
import com.example.pventure.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final UserRepository userRepository;
    private final TripRepository tripRepository;
    private final MemberService memberService;
    private final FolderService folderService;
    private final TripFinder tripFinder;
    private final TripFolderService tripFolderService;

    // ------------------ CREATE ------------------
    @Transactional
    @Override
    public TripResponseDto createTrip(Long userId, TripRequestDto dto, boolean includeMembers) {
        User user = loadUser(userId);

        Trip trip = tripRepository.save(dto.toEntity());
        memberService.registerOwner(user, trip);

        attachFolderIfPresent(dto, user, trip);

        return buildTripResponse(trip, includeMembers);
    }

    // ------------------ READ ------------------
    @Override
    public List<TripResponseDto> getTrips(Long userId, TripSearchRequestDto searchRequest, boolean includeMembers) {
        User user = loadUser(userId);

        List<Trip> trips = tripFinder.findByUserAndPeriod(user,
                            searchRequest.getStartDate(),
                            searchRequest.getEndDate(),
                            includeMembers,
                            searchRequest.getTripDateFilter());

        return trips.stream()
                .peek(trip -> checkMember(user, trip))
                .map(trip -> buildTripResponse(trip, includeMembers))
                .toList();
    }

    @Override
    public TripResponseDto getTrip(Long userId, Long tripId, boolean includeMembers) {
        User user = loadUser(userId);
        Trip trip = tripFinder.findById(tripId, includeMembers);

        checkMember(user, trip);
        return buildTripResponse(trip, includeMembers);
    }

    @Override
    public Long countTrip(Long userId) {
        User user = loadUser(userId);
        return tripRepository.countTrip(user);
    }

    @Override
    public Long countTripsWithNoDate(Long userId) {
        User user = loadUser(userId);
        return tripRepository.countTripsWithNoDate(user);
    }

    // ------------------ UPDATE ------------------
    @Transactional
    @Override
    public TripResponseDto updateTrip(Long userId, Long tripId, TripUpdateDto tripUpdateDto, boolean includeMembers) {
        User user = loadUser(userId);
        Trip trip = tripFinder.findById(tripId, includeMembers);

        checkEditable(user, trip);
        tripUpdateDto.applyTo(trip);

        attachFolderIfPresent(tripUpdateDto, user, trip);

        return buildTripResponse(trip, includeMembers);
    }

    // ------------------ DELETE ------------------
    @Transactional
    @Override
    public void deleteTrip(Long userId, Long tripId) {
        User user = loadUser(userId);
        Trip trip = tripFinder.findById(tripId, true);

        checkDeletable(user, trip);
        tripRepository.delete(trip);
    }

    // ------------------ HELPER ------------------
    private User loadUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
    }

    private void checkMember(User user, Trip trip) {
        if (!memberService.isMember(user, trip)) {
            throw new ApiException(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS);
        }
    }

    private void checkEditable(User user, Trip trip) {
        if (!memberService.canEdit(user, trip)) {
            throw new ApiException(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS);
        }
    }

    private void checkDeletable(User user, Trip trip) {
        if (!memberService.canDelete(user, trip)) {
            throw new ApiException(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS);
        }
    }

    private TripResponseDto buildTripResponse(Trip trip, boolean includeMembers) {
        Long memberCount = memberService.countMember(trip);

        List<MemberSummaryDto> members = includeMembers
                ? memberService.getMemberSummaryDtoList(trip)
                : Collections.emptyList();

        return TripResponseDto.from(trip, members, memberCount);
    }

    private <T extends FolderAttachable> void attachFolderIfPresent(T dto, User user, Trip trip) {
        Optional.ofNullable(dto.getFolderId())
                .ifPresent(folderId -> {
                    Folder folder = folderService.getFolderEntity(user, folderId);
                    tripFolderService.createTripFolder(trip, folder);
                });
    }
}
