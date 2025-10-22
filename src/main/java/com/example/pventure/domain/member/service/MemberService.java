package com.example.pventure.domain.member.service;

import com.example.pventure.domain.member.dto.request.MemberRequestDto;
import com.example.pventure.domain.member.dto.response.MemberSummaryDto;
import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.user.entity.User;

import java.util.List;

public interface MemberService {

    List<MemberSummaryDto> registerOwner(User user, Trip trip);
    List<MemberSummaryDto> inviteMember(User user, Long tripId, MemberRequestDto memberRequestDto);
}
