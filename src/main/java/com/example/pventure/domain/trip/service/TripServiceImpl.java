package com.example.pventure.domain.trip.service;

import com.example.pventure.domain.folder.entity.Folder;
import com.example.pventure.domain.folder.service.FolderService;
import com.example.pventure.domain.member.dto.response.MemberSummaryDto;
import com.example.pventure.domain.member.service.MemberService;
import com.example.pventure.domain.trip.dto.request.TripRequestDto;
import com.example.pventure.domain.trip.dto.response.TripResponseDto;
import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.trip.repository.TripRepository;
import com.example.pventure.domain.tripFolder.repository.TripFolderRepository;
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

        return TripResponseDto.from(trip,memberSummaryDtoList);
    }

    @Override
    public List<TripResponseDto> getTrips(Long userId) {
        return List.of();
    }

    @Override
    public TripResponseDto getTrip(Long userId, Long tripId) {
        return null;
    }

    @Override
    public TripResponseDto updateTrip(Long userId, Long tripId, TripRequestDto tripRequestDto) {
        return null;
    }

    @Override
    public void deleteTrip(Long userId, Long tripId) {

    }
}
