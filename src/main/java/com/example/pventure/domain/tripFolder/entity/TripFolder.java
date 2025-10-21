package com.example.pventure.domain.tripFolder.entity;

import com.example.pventure.domain.folder.entity.Folder;
import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(
        uniqueConstraints = @UniqueConstraint(
                name = "uk_tripfolder_trip_folder",
                columnNames = {"trip_id", "folder_id"}
        )
)
public class TripFolder extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id", nullable = false)
    private Folder folder;

}
