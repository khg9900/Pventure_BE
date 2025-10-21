package com.example.pventure.domain.folder.entity;

import com.example.pventure.domain.team.entity.Team;
import com.example.pventure.domain.tripFolder.entity.TripFolder;
import com.example.pventure.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Folder extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Column(nullable = false)
    private boolean isDefault;

    @Builder.Default
    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TripFolder> tripFolders = new ArrayList<>();
}
