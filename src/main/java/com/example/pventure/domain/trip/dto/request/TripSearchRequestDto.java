package com.example.pventure.domain.trip.dto.request;

import com.example.pventure.domain.trip.enums.TripDateFilter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TripSearchRequestDto {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    private TripDateFilter tripDateFilter = TripDateFilter.ALL;

}