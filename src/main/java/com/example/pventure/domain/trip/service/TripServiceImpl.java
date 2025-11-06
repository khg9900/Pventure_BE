package com.example.pventure.domain.trip.service;

import com.example.pventure.domain.folder.entity.Folder;
import com.example.pventure.domain.folder.service.FolderService;
import com.example.pventure.domain.member.dto.response.MemberSummaryDto;
import com.example.pventure.domain.member.service.MemberService;
import com.example.pventure.domain.trip.dto.request.TripRequestDto;
import com.example.pventure.domain.trip.dto.request.TripSearchRequestDto;
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

    @Transactional
    @Override
    public TripResponseDto createTrip(Long userId, TripRequestDto tripRequestDto, boolean includeMembers) {
        User user = getUserOrThrow(userId);
        Trip trip = tripRepository.save(tripRequestDto.toEntity());
        memberService.registerOwner(user, trip);

        Folder folder = folderService.getFolderEntity(user, tripRequestDto.getFolderId());
        tripFolderService.createTripFolder(trip, folder);

        return buildTripResponse(trip, includeMembers);
    }

    @Override
    public List<TripResponseDto> getTrips(Long userId, TripSearchRequestDto searchRequest, boolean includeMembers) {
        User user = getUserOrThrow(userId);
        List<Trip> trips = tripFinder.findByUserAndPeriod(user, searchRequest.getStartDate(), searchRequest.getEndDate(), includeMembers);

        return trips.stream()
                .filter(trip -> {
                    if (!memberService.isMember(user, trip)) {
                        throw new ApiException(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS);
                    }
                    return true;
                })
                .map(trip -> buildTripResponse(trip, includeMembers))
                .toList();
    }

    @Override
    public TripResponseDto getTrip(Long userId, Long tripId, boolean includeMembers) {
        User user = getUserOrThrow(userId);
        Trip trip = tripFinder.findById(tripId, includeMembers);

        if (!memberService.isMember(user, trip)) {
            throw new ApiException(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS);
        }

        return buildTripResponse(trip, includeMembers);
    }

    @Transactional
    @Override
    public TripResponseDto updateTrip(Long userId, Long tripId, TripRequestDto tripRequestDto, boolean includeMembers) {
        User user = getUserOrThrow(userId);
        Trip trip = tripFinder.findById(tripId, includeMembers);

        if (!memberService.canEdit(user, trip)) {
            throw new ApiException(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS);
        }

        updateTripEntity(trip, tripRequestDto);
        return buildTripResponse(trip, includeMembers);
    }

    @Transactional
    @Override
    public void deleteTrip(Long userId, Long tripId) {
        User user = getUserOrThrow(userId);
        Trip trip = tripFinder.findById(tripId, true);

        if (!memberService.canDelete(user, trip)) {
            throw new ApiException(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS);
        }

        tripRepository.delete(trip);
    }

    private User getUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
    }

    private TripResponseDto buildTripResponse(Trip trip, boolean includeMembers) {
        List<MemberSummaryDto> members = Collections.emptyList();
        if (includeMembers) {
            members = memberService.getMemberSummaryDtoList(trip);
        }
        return TripResponseDto.from(trip, members);
    }

    private void updateTripEntity(Trip trip, TripRequestDto dto) {
        if (dto.getTitle() != null) trip.updateTitle(dto.getTitle());
        if (dto.getDestination() != null) trip.updateDestination(dto.getDestination());
        trip.updateDates(dto.getStartDate(), dto.getEndDate());
    }
}

