package com.example.pventure.domain.member.repository;

import com.example.pventure.domain.member.entity.Member;
import com.example.pventure.domain.member.enums.MemberRole;
import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m JOIN FETCH m.user WHERE m.trip = :trip")
    List<Member> findByTripWithUser(@Param("trip") Trip trip);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END " +
            "FROM Member m WHERE m.trip = :trip AND m.user = :user")
    boolean existsByTripAndUser(@Param("trip") Trip trip, @Param("user") User user);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END " +
            "FROM Member m WHERE m.trip = :trip AND m.user = :user AND m.memberRole IN :roles")
    boolean existsByTripAndUserAndMemberRoleIn(@Param("trip") Trip trip, @Param("user") User user, @Param("roles") List<MemberRole> roles);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END " +
            "FROM Member m WHERE m.trip = :trip AND m.user = :user AND m.memberRole = :role")
    boolean existsByTripAndUserAndMemberRole(@Param("trip") Trip trip, @Param("user") User user, @Param("role") MemberRole role);
}
