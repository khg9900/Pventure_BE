package com.example.pventure.domain.schedule.service;

import com.example.pventure.domain.member.service.MemberService;
import com.example.pventure.domain.schedule.dto.request.ScheduleReorderRequestDto;
import com.example.pventure.domain.schedule.dto.request.ScheduleRequestDto;
import com.example.pventure.domain.schedule.dto.request.ScheduleUpdateDto;
import com.example.pventure.domain.schedule.dto.response.ScheduleResponseDto;
import com.example.pventure.domain.schedule.entity.Schedule;
import com.example.pventure.domain.schedule.enums.TimeSlot;
import com.example.pventure.domain.schedule.repository.ScheduleRepository;
import com.example.pventure.domain.schedule.util.ScheduleSequenceUtil;
import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.trip.enums.TripStatus;
import com.example.pventure.domain.trip.repository.TripRepository;
import com.example.pventure.domain.user.entity.User;
import com.example.pventure.domain.user.enums.SocialProvider;
import com.example.pventure.domain.user.repository.UserRepository;
import com.example.pventure.global.exception.ApiException;
import com.example.pventure.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @Mock private ScheduleRepository scheduleRepository;
    @Mock private TripRepository tripRepository;
    @Mock private UserRepository userRepository;
    @Mock private ScheduleSequenceUtil sequenceUtil;
    @Mock private MemberService memberService;

    @InjectMocks private ScheduleServiceImpl scheduleService;

    private User user;
    private Trip trip;
    private Schedule schedule;

    @BeforeEach
    void setUp() throws Exception {
        user = User.builder()
                .name("홍길동")
                .email("hong@example.com")
                .socialProvider(SocialProvider.GOOGLE)
                .providerId("google-id-123")
                .build();
        setId(user, 1L);

        trip = Trip.builder()
                .title("제주 여행")
                .status(TripStatus.COMPLETED)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(3))
                .build();
        setId(trip, 1L);

        schedule = Schedule.builder()
                .trip(trip)
                .day(1)
                .sequence(1)
                .memo("테스트 코드")
                .build();
        setId(schedule, 1L);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
    }

    private void setId(Object entity, Long id) throws Exception {
        Field idField = entity.getClass().getSuperclass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(entity, id);
    }

    @Test
    @DisplayName("createSchedule 성공")
    void createSchedule_success() {
        when(tripRepository.findById(anyLong())).thenReturn(Optional.of(trip));
        when(sequenceUtil.shiftOnInsert(trip.getId(), 1, 1)).thenReturn(1);
        when(scheduleRepository.save(any(Schedule.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(memberService.canEdit(user, trip)).thenReturn(true);

        ScheduleRequestDto dto = new ScheduleRequestDto(1, 1, false,"테스트 메모",TimeSlot.AFTER_LUNCH);
        ScheduleResponseDto response = scheduleService.createSchedule(trip.getId(), user.getId(), dto);

        verify(sequenceUtil).shiftOnInsert(trip.getId(), 1, 1);
        verify(scheduleRepository).save(any(Schedule.class));
        assertEquals(1, response.getDay());
        assertEquals(1, response.getSequence());
    }

    @Test
    @DisplayName("getSchedules 성공")
    void getSchedules_success() {
        when(tripRepository.findById(anyLong())).thenReturn(Optional.of(trip));
        when(scheduleRepository.findByTripAndDay(trip, 1)).thenReturn(List.of(schedule));
        when(memberService.isMember(user, trip)).thenReturn(true);

        List<ScheduleResponseDto> response = scheduleService.getSchedules(trip.getId(), user.getId(), 1);
        assertEquals(1, response.size());
    }

    @Test
    @DisplayName("updateSchedule 성공")
    void updateSchedule_success() {
        when(tripRepository.findById(trip.getId())).thenReturn(Optional.of(trip));
        when(scheduleRepository.findById(schedule.getId())).thenReturn(Optional.of(schedule));
        when(memberService.canEdit(user, trip)).thenReturn(true);

        ScheduleUpdateDto dto = new ScheduleUpdateDto(true,"테스트 코드 메모");
        ScheduleResponseDto response = scheduleService.updateSchedule(trip.getId(), schedule.getId(), user.getId(), dto);
        assertTrue(response.isCompleted());
    }

    @Test
    @DisplayName("reorderSchedules 성공")
    void reorderSchedules_success() {
        when(tripRepository.findById(trip.getId())).thenReturn(Optional.of(trip));
        when(scheduleRepository.findByTripAndDayAndSequence(trip.getId(), 1, 1))
                .thenReturn(Optional.of(schedule));
        when(scheduleRepository.findByTripAndDay(trip, 1)).thenReturn(List.of(schedule));
        when(memberService.canEdit(user, trip)).thenReturn(true);

        ScheduleReorderRequestDto dto = new ScheduleReorderRequestDto(1L, 2, 1,TimeSlot.AFTER_EVENING);
        List<ScheduleResponseDto> response = scheduleService.reorderSchedules(trip.getId(), user.getId(), 1, dto);

        verify(sequenceUtil).reorder(trip.getId(), 1, 1, 2);
        assertEquals(1, response.size());
        assertEquals(2, response.get(0).getSequence());
        assertEquals(TimeSlot.AFTER_EVENING, response.get(0).getTimeSlot());
    }

    @Test
    @DisplayName("deleteSchedule 성공")
    void deleteSchedule_success() {
        when(tripRepository.findById(trip.getId())).thenReturn(Optional.of(trip));
        when(scheduleRepository.findById(schedule.getId())).thenReturn(Optional.of(schedule));
        when(memberService.canEdit(user, trip)).thenReturn(true);

        scheduleService.deleteSchedule(trip.getId(), schedule.getId(), user.getId());

        verify(scheduleRepository).delete(schedule);
        verify(sequenceUtil).shiftOnDelete(trip.getId(), 1, 1);
    }

    @Test
    @DisplayName("createSchedule 실패 - 없는 유저")
    void createSchedule_fail_userNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        ScheduleRequestDto dto = new ScheduleRequestDto(1, 1, false, "메모",TimeSlot.AFTER_LUNCH);

        ApiException ex = assertThrows(ApiException.class,
                () -> scheduleService.createSchedule(trip.getId(), 999L, dto));
        assertEquals(ErrorCode.NOT_FOUND_USER, ex.getErrorCode());
    }

    @Test
    @DisplayName("getSchedules 실패 - 없는 여행")
    void getSchedules_fail_tripNotFound() {
        when(tripRepository.findById(anyLong())).thenReturn(Optional.empty());

        ApiException ex = assertThrows(ApiException.class,
                () -> scheduleService.getSchedules(999L, user.getId(), 1));
        assertEquals(ErrorCode.NOT_FOUND_TRIP, ex.getErrorCode());
    }

    @Test
    @DisplayName("updateSchedule 실패 - 없는 일정")
    void updateSchedule_fail_scheduleNotFound() {
        when(tripRepository.findById(trip.getId())).thenReturn(Optional.of(trip));
        when(scheduleRepository.findById(999L)).thenReturn(Optional.empty());
        when(memberService.canEdit(user, trip)).thenReturn(true);

        ScheduleUpdateDto dto = new ScheduleUpdateDto(true, "테스트 메모");
        ApiException ex = assertThrows(ApiException.class,
                () -> scheduleService.updateSchedule(trip.getId(), 999L, user.getId(), dto));
        assertEquals(ErrorCode.NOT_FOUND_SCHEDULE, ex.getErrorCode());
    }

    @Test
    @DisplayName("reorderSchedules 실패 - 없는 일정")
    void reorderSchedules_fail_scheduleNotFound() {
        when(tripRepository.findById(trip.getId())).thenReturn(Optional.of(trip));
        when(scheduleRepository.findByTripAndDayAndSequence(trip.getId(), 1, 1)).thenReturn(Optional.empty());
        when(memberService.canEdit(user, trip)).thenReturn(true);

        ScheduleReorderRequestDto dto = new ScheduleReorderRequestDto(1L, 2, 1,TimeSlot.MORNING);
        ApiException ex = assertThrows(ApiException.class,
                () -> scheduleService.reorderSchedules(trip.getId(), user.getId(), 1, dto));
        assertEquals(ErrorCode.NOT_FOUND_SCHEDULE, ex.getErrorCode());
    }

    @Test
    @DisplayName("deleteSchedule 실패 - 없는 일정")
    void deleteSchedule_fail_scheduleNotFound() {
        when(tripRepository.findById(trip.getId())).thenReturn(Optional.of(trip));
        when(scheduleRepository.findById(999L)).thenReturn(Optional.empty());
        when(memberService.canEdit(user, trip)).thenReturn(true);

        ApiException ex = assertThrows(ApiException.class,
                () -> scheduleService.deleteSchedule(trip.getId(), 999L, user.getId()));
        assertEquals(ErrorCode.NOT_FOUND_SCHEDULE, ex.getErrorCode());
    }
}
