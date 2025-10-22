package com.example.pventure.domain.schedule.entity;

import com.example.pventure.domain.place.entity.Place;
import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private LocalDate date;

    @Column(nullable = false)
    private Integer day;

    @Column(nullable = false)
    private Integer sequence;

    @Column(length = 500)
    private String memo;

    @Builder.Default
    @Column(nullable = false)
    private boolean isCompleted = false;

    @Builder.Default
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Place> places = new ArrayList<>();


}
