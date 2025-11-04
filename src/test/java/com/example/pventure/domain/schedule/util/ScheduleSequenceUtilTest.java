package com.example.pventure.domain.schedule.util;

import com.example.pventure.domain.schedule.entity.Schedule;
import com.example.pventure.domain.schedule.repository.ScheduleRepository;
import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.trip.enums.TripStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleSequenceUtilTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ScheduleSequenceUtil sequenceUtil;

    private Trip trip;
    private Schedule schedule;

    @BeforeEach
    void setUp() throws Exception {
        trip = Trip.builder()
                .title("제주 여행")
                .destination("제주")
                .status(TripStatus.PLANNED)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(3))
                .build();
        setId(trip,1L);

        schedule = Schedule.builder()
                .sequence(1)
                .day(1)
                .isCompleted(false)
                .memo("테스트 메모")
                .trip(trip)
                .build();
    }


    private void setId(Object entity, Long id) throws Exception {
        Field idField = entity.getClass().getSuperclass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(entity, id);
    }

    @Test
    @DisplayName("새 일정이 삽입될 때 이후 일정 시퀀스가 밀려야 한다")
    void shiftOnInsert_shouldCallIncrement() {
        // given
        int insertSeq = 1;

        // when
        sequenceUtil.shiftOnInsert(trip.getId(), schedule.getDay(), insertSeq);

        // then
        verify(scheduleRepository, times(1))
                .incrementSequence(trip.getId(), schedule.getDay(), insertSeq, Integer.MAX_VALUE);

    }

    @Test
    @DisplayName("기존 일정 재정렬 시 newSeq < oldSeq 면 incrementSequence가 불려야 한다")
    void reorder_shouldIncrement_whenNewSeqIsSmaller() {
        // given
        int oldSeq = 5;
        int newSeq = 2;

        // when
        sequenceUtil.reorder(trip.getId(), schedule.getDay(), oldSeq, newSeq);

        // then
        verify(scheduleRepository).incrementSequence(trip.getId(), schedule.getDay(), newSeq, oldSeq - 1);
        verify(scheduleRepository, never()).decrementSequence(any(), anyInt(), anyInt(), anyInt());
    }

    @Test
    @DisplayName("기존 일정 재정렬 시 newSeq > oldSeq 면 decrementSequence가 불려야 한다")
    void reorder_shouldDecrement_whenNewSeqIsGreater() {
        // given
        int oldSeq = 2;
        int newSeq = 5;

        // when
        sequenceUtil.reorder(trip.getId(), schedule.getDay(), oldSeq, newSeq);

        // then
        verify(scheduleRepository).decrementSequence(trip.getId(), schedule.getDay(), oldSeq + 1, newSeq);
        verify(scheduleRepository, never()).incrementSequence(any(), anyInt(), anyInt(), anyInt());
    }

    @Test
    @DisplayName("삭제 시 삭제된 시퀀스 이후의 일정이 당겨져야 한다")
    void shiftOnDelete_shouldCallDecrement() {
        // given
        int deletedSeq = 3;

        // when
        sequenceUtil.shiftOnDelete(trip.getId(), schedule.getDay(), deletedSeq);

        // then
        verify(scheduleRepository)
                .decrementSequence(trip.getId(), schedule.getDay(), deletedSeq + 1, Integer.MAX_VALUE);
        verifyNoMoreInteractions(scheduleRepository);
    }
}
