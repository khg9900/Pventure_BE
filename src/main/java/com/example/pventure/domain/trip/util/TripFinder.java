package com.example.pventure.domain.trip.util;

import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.trip.enums.TripDateFilter;
import com.example.pventure.domain.trip.repository.TripRepository;
import com.example.pventure.domain.user.entity.User;
import com.example.pventure.global.exception.ApiException;
import com.example.pventure.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Component
public class TripFinder {

    private final TripRepository tripRepository;

    public Trip findById(Long tripId, boolean includeMembers) {
        if (includeMembers) {
            return tripRepository.findByIdWithMemberAndUser(tripId)
                    .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TRIP));
        }
        return tripRepository.findByIdWithMember(tripId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TRIP));
    }

    public List<Trip> findByUserAndPeriod(User user, LocalDate start, LocalDate end,
                                          boolean includeMembers, TripDateFilter tripDateFilter) {

        return switch (tripDateFilter) {
            case ALL -> findAll(user, includeMembers);
            case SPECIFIED -> findSpecified(user, start, end, includeMembers);
            case UNSPECIFIED -> findUnspecified(user, includeMembers);
        };
    }

    private List<Trip> findAll(User user, boolean includeMembers) {
        return includeMembers
                ? tripRepository.findByUserWithMember(user)
                : tripRepository.findByUser(user);
    }

    private List<Trip> findSpecified(User user, LocalDate start, LocalDate end, boolean includeMembers) {
        return includeMembers
                ? tripRepository.findByUserAndPeriodWithUser(user, start, end)
                :   tripRepository.findByUserAndPeriod(user, start, end);
    }

    private List<Trip> findUnspecified(User user, boolean includeMembers) {
        return includeMembers
                ? tripRepository.findByUserAndPeriodWithUser(user, null, null)
                : tripRepository.findByUserAndPeriod(user, null, null);

    }
}
