package com.example.pventure.domain.trip.util;

import com.example.pventure.domain.trip.entity.Trip;
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

    public List<Trip> findByUserAndPeriod(User user, LocalDate start, LocalDate end, boolean includeMembers) {
        if (includeMembers) {
            return tripRepository.findByUserAndPeriodWithUser(user, start, end);
        }
        return tripRepository.findByUserAndPeriod(user, start, end);
    }
}
