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
public class Member extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    Team team;

    @Enumerated(EnumType.STRING)
    TeamRole teamRole;

    @Enumerated(EnumType.STRING)
    TeamStatus teamStatus;


}
