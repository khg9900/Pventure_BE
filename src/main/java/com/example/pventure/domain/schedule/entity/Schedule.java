package com.example.pventure.domain.schedule.entity;

import com.example.pventure.domain.schedule.enums.TimeSlot;
import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Schedule extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @Column(nullable = false)
    private Integer day;

    @Column(nullable = false)
    private Integer sequence;

    @Column(length = 500)
    private String memo;

    @Builder.Default
    @Column(nullable = false)
    private boolean isCompleted = false;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TimeSlot timeSlot;

    public void updateSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public void updateCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public void updateMemo(String memo) {
        this.memo = memo;
    }

    public void updateTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

}
