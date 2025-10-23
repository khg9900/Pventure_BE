package com.example.pventure.domain.member.service;

import com.example.pventure.domain.member.dto.request.MemberRequestDto;
import com.example.pventure.domain.member.dto.response.MemberSummaryDto;
import com.example.pventure.domain.member.entity.Member;
import com.example.pventure.domain.member.enums.MemberRole;
import com.example.pventure.domain.member.enums.MemberStatus;
import com.example.pventure.domain.member.repository.MemberRepository;
import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.trip.repository.TripRepository;
import com.example.pventure.domain.user.entity.User;
import com.example.pventure.global.exception.ApiException;
import com.example.pventure.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final TripRepository tripRepository;

    @Override
    @Transactional
    public List<MemberSummaryDto> registerOwner(User user, Trip trip) {
        Member owner = new MemberRequestDto(MemberRole.OWNER, MemberStatus.ACCEPTED)
                .toEntity(user, trip);

        memberRepository.save(owner);

        return List.of(MemberSummaryDto.from(owner));
    }

    @Override
    @Transactional
    public List<MemberSummaryDto> inviteMember(User user, Long tripId, MemberRequestDto memberRequestDto) {
        Trip trip = tripRepository.findById(tripId).
                orElseThrow(()-> new ApiException(ErrorCode.NOT_FOUND_TRIP)); // 인터페이스 메서드 사용

        Member invitee = memberRequestDto.toEntity(user, trip);

        memberRepository.save(invitee);

        return this.getMemberSummaryDtoList(trip);
    }

    @Override
    public List<Member> getMembers(Trip trip) {
        return memberRepository.findByTrip(trip).stream()
                .toList();
    }

    @Override
    public List<MemberSummaryDto> getMemberSummaryDtoList(Trip trip) {
        return memberRepository.findByTrip(trip).stream()
                .map(MemberSummaryDto::from)
                .toList();
    }

    @Override
    public List<Trip> getTripsByUser(User user) {
        return memberRepository.findByUser(user).stream()
                .map(Member::getTrip)
                .toList();
    }


}
