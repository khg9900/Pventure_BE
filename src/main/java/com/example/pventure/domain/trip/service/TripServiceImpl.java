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
import com.example.pventure.domain.tripFolder.service.TripFolderService;
import com.example.pventure.domain.user.entity.User;
import com.example.pventure.domain.user.repository.UserRepository;
import com.example.pventure.global.exception.ApiException;
import com.example.pventure.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final UserRepository userRepository;
    private final TripRepository tripRepository;
    private final MemberService memberService;
    private final FolderService folderService;
    private final TripFolderService tripFolderService;

    @Transactional
    @Override
    public TripResponseDto createTrip(Long userId, TripRequestDto tripRequestDto) {
        User user = getUserOrThrow(userId);

        Trip trip = tripRepository.save(tripRequestDto.toEntity());

        List<MemberSummaryDto> members = memberService.registerOwner(user, trip);

        Folder folder = folderService.getFolderEntity(user,tripRequestDto.getFolderId());

        tripFolderService.createTripFolder(trip,folder);

        return TripResponseDto.from(trip, members);
    }

    @Override
    public List<TripResponseDto> getTrips(Long userId, TripSearchRequestDto searchRequest) {
        User user = getUserOrThrow(userId);

        List<Trip> trips = tripRepository.findByUserAndPeriod(
                user, searchRequest.getStartDate(), searchRequest.getEndDate()
        );

        return trips.stream()
                .map(trip -> TripResponseDto.from(trip, memberService.getMemberSummaryDtoList(trip)))
                .toList();
    }

    @Override
    public TripResponseDto getTrip(Long userId, Long tripId) {
        User user = getUserOrThrow(userId);
        Trip trip = getTripOrThrow(tripId);

        if (!memberService.isMember(user, trip)) {
            throw new ApiException(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS);
        }

        return TripResponseDto.from(trip, memberService.getMemberSummaryDtoList(trip));
    }

    @Transactional
    @Override
    public TripResponseDto updateTrip(Long userId, Long tripId, TripRequestDto tripRequestDto) {
        User user = getUserOrThrow(userId);
        Trip trip = getTripOrThrow(tripId);

        if (!memberService.canEdit(user, trip)) {
            throw new ApiException(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS);
        }

        if (tripRequestDto.getTitle() != null) trip.updateTitle(tripRequestDto.getTitle());
        if (tripRequestDto.getDestination() != null) trip.updateDestination(tripRequestDto.getDestination());
        trip.updateDates(tripRequestDto.getStartDate(), tripRequestDto.getEndDate());

        return TripResponseDto.from(trip, memberService.getMemberSummaryDtoList(trip));
    }

    @Transactional
    @Override
    public void deleteTrip(Long userId, Long tripId) {
        User user = getUserOrThrow(userId);
        Trip trip = getTripOrThrow(tripId);

        if (!memberService.canDelete(user, trip)) {
            throw new ApiException(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS);
        }

        tripRepository.delete(trip);
    }

    private User getUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
    }

    private Trip getTripOrThrow(Long tripId) {
        return tripRepository.findById(tripId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TRIP));
    }
}
