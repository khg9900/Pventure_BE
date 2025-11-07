package com.example.pventure.domain.member.dto.request;

import com.example.pventure.domain.member.entity.Member;
import com.example.pventure.domain.member.enums.MemberRole;
import com.example.pventure.domain.member.enums.MemberStatus;
import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRequestDto {

    private MemberRole memberRole;

    private MemberStatus memberStatus;

    public Member toEntity(User user, Trip trip) {
        return Member.builder()
                .user(user)
                .trip(trip)
                .memberRole(memberRole != null ? memberRole : MemberRole.VIEWER)
                .memberStatus(memberStatus != null ? memberStatus : MemberStatus.PENDING)
                .build();
    }

}
