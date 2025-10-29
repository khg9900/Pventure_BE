package com.example.pventure.domain.trip.entity;

import com.example.pventure.domain.member.entity.Member;
import com.example.pventure.domain.trip.enums.TripStatus;
import com.example.pventure.domain.tripFolder.entity.TripFolder;
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
public class Trip extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false)
    private String destination;

    @Column(columnDefinition = "TEXT")
    private String thumbnail;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private TripStatus status;

    @Builder.Default
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TripFolder> folders = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "trip", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    public void updateTitle(String title) {
        if(title != null) this.title = title;
    }

    public void updateDestination(String destination) {
        if(destination != null) this.destination = destination;
    }

    public void updateTripStatus(TripStatus tripStatus){
        if(tripStatus != null) this.status = tripStatus;
    }

    public void updateDates(LocalDate startDate, LocalDate endDate) {
         this.startDate = startDate;
         this.endDate = endDate;
    }


}
