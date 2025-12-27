package com.example.pventure.domain.trip.service;

import com.example.pventure.domain.member.service.MemberService;
import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.trip.repository.TripRepository;
import com.example.pventure.domain.user.entity.User;
import com.example.pventure.domain.user.repository.UserRepository;
import com.example.pventure.global.exception.ApiException;
import com.example.pventure.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TripPermissionService {

    private final UserRepository userRepository;
    private final TripRepository tripRepository;
    private final MemberService memberService;

    public Trip getEditableTrip(Long userId, Long tripId) {
        User user = loadUser(userId);
        Trip trip = loadTrip(tripId);

        if (!memberService.canEdit(user, trip)) {
            throw new ApiException(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS);
        }

        return trip;
    }

    public Trip getViewableTrip(Long userId, Long tripId) {
        User user = loadUser(userId);
        Trip trip = loadTrip(tripId);

        if (!memberService.isMember(user, trip)) {
            throw new ApiException(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS);
        }

        return trip;
    }

    public void checkEditableTrip(User user, Trip trip) {
        if (!memberService.canEdit(user, trip)) {
            throw new ApiException(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS);
        }
    }

    public void checkViewableTrip(User user, Trip trip) {
        if (!memberService.isMember(user, trip)) {
            throw new ApiException(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS);
        }
    }

    private User loadUser(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
    }

    private Trip loadTrip(Long tripId) {
        return tripRepository.findById(tripId)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TRIP));
    }
}