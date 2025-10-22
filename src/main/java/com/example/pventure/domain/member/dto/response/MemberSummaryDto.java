package com.example.pventure.domain.member.dto.response;

import com.example.pventure.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberSummaryDto{

    private Long id;

    private String name;

    private String email;

    public static MemberSummaryDto from(Member member) {
        return MemberSummaryDto.builder()
                .id(member.getId())
                .name(member.getUser().getName())
                .email(member.getUser().getEmail())
                .build();
    }
}
