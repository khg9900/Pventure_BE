package com.example.pventure.domain.schedule.service;

import com.example.pventure.domain.member.service.MemberService;
import com.example.pventure.domain.schedule.dto.request.ScheduleReorderRequestDto;
import com.example.pventure.domain.schedule.dto.request.ScheduleRequestDto;
import com.example.pventure.domain.schedule.dto.request.ScheduleUpdateDto;
import com.example.pventure.domain.schedule.dto.response.ScheduleResponseDto;
import com.example.pventure.domain.schedule.entity.Schedule;
import com.example.pventure.domain.schedule.repository.ScheduleRepository;
import com.example.pventure.domain.schedule.util.ScheduleSequenceUtil;
import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.trip.repository.TripRepository;
import com.example.pventure.domain.user.entity.User;
import com.example.pventure.domain.user.repository.UserRepository;
import com.example.pventure.global.exception.ApiException;
import com.example.pventure.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final MemberService memberService;
    private final ScheduleSequenceUtil sequenceUtil;

    @Override
    @Transactional
    public ScheduleResponseDto createSchedule(Long tripId, Long userId, ScheduleRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TRIP));

        if (!memberService.canEdit(user, trip)){
            throw new ApiException(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS);
        }

        Integer finalSeq = sequenceUtil.shiftOnInsert(tripId, dto.getDay(), dto.getSequence());
        Schedule schedule = scheduleRepository.save(dto.toEntity(trip, finalSeq));

        return ScheduleResponseDto.from(schedule);
    }

    @Override
    public List<ScheduleResponseDto> getSchedules(Long tripId, Long userId, Integer day) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TRIP));

        if (!memberService.isMember(user, trip)) {
            throw new ApiException(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS);
        }

        List<Schedule> schedules = scheduleRepository.findByTripAndDay(trip, day);
        return schedules.stream()
                .map(ScheduleResponseDto::from)
                .toList();
    }

    @Override
    @Transactional
    public List<ScheduleResponseDto> reorderSchedules(Long tripId, Long userId, Integer day, ScheduleReorderRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TRIP));

        if (!memberService.canEdit(user, trip)){
            throw new ApiException(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS);
        }

        Schedule target = scheduleRepository.findByTripAndDayAndSequence(tripId, day, dto.getOldSeq())
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_SCHEDULE));

        sequenceUtil.reorder(tripId, day, dto.getOldSeq(), dto.getNewSeq());

        target.updateSequence(dto.getNewSeq());

        List<Schedule> schedules = scheduleRepository.findByTripAndDay(trip, day);
        return schedules.stream()
                .map(ScheduleResponseDto::from)
                .toList();
    }

    @Override
    public ScheduleResponseDto getSchedule(Long tripId, Long scheduleId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TRIP));

        if (!memberService.isMember(user, trip)) {
            throw new ApiException(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS);
        }

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_SCHEDULE));

        if (!schedule.getTrip().getId().equals(tripId)) {
            throw new ApiException(ErrorCode.NOT_FOUND_SCHEDULE);
        }

        return ScheduleResponseDto.from(schedule);
    }

    @Override
    @Transactional
    public ScheduleResponseDto updateSchedule(Long tripId, Long scheduleId, Long userId, ScheduleUpdateDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TRIP));

        if (!memberService.canEdit(user, trip)){
            throw new ApiException(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS);
        }

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_SCHEDULE));

        if (!schedule.getTrip().getId().equals(tripId)) {
            throw new ApiException(ErrorCode.NOT_FOUND_SCHEDULE);
        }

        if (dto.getMemo() != null) schedule.updateMemo(dto.getMemo());
        if (dto.getIsComplete() != null) schedule.updateCompleted(dto.getIsComplete());

        return ScheduleResponseDto.from(schedule);
    }

    @Override
    @Transactional
    public void deleteSchedule(Long tripId, Long scheduleId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TRIP));

        if (!memberService.canEdit(user, trip)){
            throw new ApiException(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS);
        }

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_SCHEDULE));

        if (!schedule.getTrip().getId().equals(tripId)) {
            throw new ApiException(ErrorCode.NOT_FOUND_SCHEDULE);
        }

        int day = schedule.getDay();
        int deletedSeq = schedule.getSequence();

        scheduleRepository.delete(schedule);
        sequenceUtil.shiftOnDelete(tripId, day, deletedSeq);
    }
}
