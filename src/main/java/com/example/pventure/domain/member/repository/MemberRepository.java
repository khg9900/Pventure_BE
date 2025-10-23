package com.example.pventure.domain.member.repository;

import com.example.pventure.domain.member.entity.Member;
import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByTrip(Trip trip);

    List<Member> findByUser(User user);
}
