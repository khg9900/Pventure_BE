package com.example.pventure.domain.place.entity;

import com.example.pventure.domain.place.enums.PlaceCategory;
import com.example.pventure.domain.schedule.entity.Schedule;
import com.example.pventure.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Place extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String link;

    @Column(length = 500)
    private String address;

    @Column
    @DecimalMax(value = "90.0")
    @DecimalMin(value = "-90.0")
    private Double latitude;

    @Column
    @DecimalMax(value = "180.0")
    @DecimalMin(value = "-180.0")
    private Double longitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlaceCategory placeCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;
}
