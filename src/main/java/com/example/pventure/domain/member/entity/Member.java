package com.example.pventure.domain.member.entity;

import com.example.pventure.domain.member.enums.TeamRole;
import com.example.pventure.domain.member.enums.TeamStatus;
import com.example.pventure.domain.team.entity.Team;
import com.example.pventure.domain.user.entity.User;
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
                name = "uk_member_user_team",
                columnNames = {"user_id", "team_id"}
        )
)
public class Member extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TeamRole teamRole;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TeamStatus teamStatus;


}
