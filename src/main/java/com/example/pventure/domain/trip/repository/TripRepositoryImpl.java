package com.example.pventure.domain.trip.repository;

import com.example.pventure.domain.member.entity.QMember;
import com.example.pventure.domain.trip.entity.QTrip;
import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.user.entity.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class TripRepositoryImpl implements TripRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<Trip> findByUserAndDateRange(User user, LocalDate startDate, LocalDate endDate) {
        QTrip trip = QTrip.trip;
        QMember member = QMember.member;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(member.user.eq(user));

        if (startDate != null && endDate != null) {
            builder.and(trip.startDate.isNotNull())
                    .and(trip.endDate.isNotNull())
                    .and(trip.startDate.loe(endDate))
                    .and(trip.endDate.goe(startDate));
                    log.info(startDate.toString(),endDate.toString());
        } else {
            builder.and(trip.startDate.isNull())
                    .and(trip.endDate.isNull());
        }

        return queryFactory
                .selectFrom(trip)
                .join(trip.members, member)
                .fetchJoin()
                .where(builder)
                .distinct()
                .fetch();
    }
}
