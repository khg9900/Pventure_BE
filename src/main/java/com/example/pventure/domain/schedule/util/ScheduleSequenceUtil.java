package com.example.pventure.domain.schedule.util;

import com.example.pventure.domain.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ScheduleSequenceUtil {
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public int shiftOnInsert(Long tripId, int day, Integer requestedSequence) {
        int maxSeq = scheduleRepository.findMaxSequence(tripId, day);

        if (requestedSequence == null || requestedSequence > maxSeq + 1) {
            return maxSeq + 1;
        }

        scheduleRepository.incrementSequence(tripId, day, requestedSequence, Integer.MAX_VALUE);
        return requestedSequence;
    }

    @Transactional
    public void reorder(Long tripId, int day, int oldSeq, int newSeq) {
        if (newSeq < oldSeq) {
            scheduleRepository.incrementSequence(tripId, day, newSeq, oldSeq - 1);
        } else if (newSeq > oldSeq) {
            scheduleRepository.decrementSequence(tripId, day, oldSeq + 1, newSeq);
        }
    }

    @Transactional
    public void shiftOnDelete(Long tripId, int day, int deletedSeq) {
        scheduleRepository.decrementSequence(tripId, day, deletedSeq + 1, Integer.MAX_VALUE);
    }

}
