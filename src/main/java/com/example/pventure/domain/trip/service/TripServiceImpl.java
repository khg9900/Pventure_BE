package com.example.pventure.domain.trip.service;

import com.example.pventure.domain.folder.entity.Folder;
import com.example.pventure.domain.folder.service.FolderService;
import com.example.pventure.domain.member.dto.response.MemberSummaryDto;
import com.example.pventure.domain.member.entity.Member;
import com.example.pventure.domain.member.enums.MemberRole;
import com.example.pventure.domain.member.service.MemberService;
import com.example.pventure.domain.trip.dto.request.TripRequestDto;
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
public class TripServiceImpl implements TripService{

    private final UserRepository userRepository;
    private final TripRepository tripRepository;
    private final MemberService memberService;
    private final FolderService folderService;
    private final TripFolderService tripFolderService;

    @Transactional
    @Override
    public TripResponseDto createTrip(Long userId, TripRequestDto tripRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));

        Trip trip = tripRepository.save(tripRequestDto.toEntity());

        List<MemberSummaryDto> memberSummaryDtoList = memberService.registerOwner(user, trip);

        Folder folder = folderService.getUserDefaultFolder(user);
        tripFolderService.createTripFolder(trip, folder);

        return TripResponseDto.from(trip, memberSummaryDtoList);
    }

    @Override
    public List<TripResponseDto> getTrips(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));

        List<Trip> trips = memberService.getTripsByUser(user);

        return trips.stream()
                .map(trip -> TripResponseDto.from(trip, memberService.getMemberSummaryDtoList(trip)))
                .toList();
    }

    @Override
    public TripResponseDto getTrip(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TRIP));

        return TripResponseDto.from(trip, memberService.getMemberSummaryDtoList(trip));
    }

    @Override
    public TripResponseDto updateTrip(Long userId, Long tripId, TripRequestDto tripRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));

        Trip trip= tripRepository.findById(tripId)
                .orElseThrow(()->new ApiException(ErrorCode.NOT_FOUND_TRIP));

        boolean isMember = memberService.getMembers(trip).stream()
                .anyMatch(member -> member.getUser().equals(user));

        if (!isMember) {
            throw new ApiException(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS);
        }

        if(tripRequestDto.getTitle() != null) trip.updateTitle(tripRequestDto.getTitle());
        if(tripRequestDto.getDestination() != null) trip.updateDestination(tripRequestDto.getDestination());
        trip.updateDates(tripRequestDto.getStartDate(), tripRequestDto.getEndDate());

        List<MemberSummaryDto> memberSummaryDtoList = memberService.getMemberSummaryDtoList(trip);

        return TripResponseDto.from(trip, memberSummaryDtoList);
    }

    @Override
    public void deleteTrip(Long userId, Long tripId) {
        // 1. User 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));

        // 2. Trip 조회
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TRIP));

        // 3. User가 Trip Owner인지 확인 (삭제 권한 체크)
        boolean isOwner = memberService.getMembers(trip).stream()
                .anyMatch(member -> member.getUser().equals(user) &&
                        member.getMemberRole() == MemberRole.OWNER);

        if (!isOwner) {
            throw new ApiException(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS);
        }

        // 4. Trip 삭제
        tripRepository.delete(trip);
    }


}
