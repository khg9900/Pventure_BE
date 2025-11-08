package com.example.pventure.domain.trip.controller;

import com.example.pventure.domain.trip.dto.request.TripRequestDto;
import com.example.pventure.domain.trip.dto.request.TripSearchRequestDto;
import com.example.pventure.domain.trip.dto.response.TripResponseDto;
import com.example.pventure.domain.trip.service.TripService;
import com.example.pventure.global.response.CustomResponse;
import com.example.pventure.global.response.CustomResponseHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Trip", description = "여행 관련 API")
@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @Operation(
            summary = "여행 생성",
            description = "사용자 ID와 여행 정보를 입력하여 새로운 여행을 생성합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "생성 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content)
    })
    @PostMapping
    public ResponseEntity<CustomResponse<TripResponseDto>> createTrip(
            @Parameter(description = "사용자 ID", required = true, example = "1") @RequestParam Long userId,
            @Parameter(description = "여행 생성 요청 DTO", required = true) @RequestBody TripRequestDto tripRequestDto
    ) {
        TripResponseDto response = tripService.createTrip(userId, tripRequestDto);
        return CustomResponseHelper.created(response);
    }

    @Operation(
            summary = "여행 목록 조회",
            description = "사용자 ID와 검색 조건으로 여행 목록을 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content)
    })
    @GetMapping
    public ResponseEntity<CustomResponse<List<TripResponseDto>>> getTrips(
            @Parameter(description = "검색 조건 DTO") @ModelAttribute TripSearchRequestDto searchRequest,
            @Parameter(description = "사용자 ID", required = true, example = "1") @RequestParam Long userId
    ) {
        List<TripResponseDto> trips = tripService.getTrips(userId, searchRequest);
        return CustomResponseHelper.ok(trips);
    }

    @Operation(
            summary = "특정 여행 조회",
            description = "사용자 ID와 여행 ID로 특정 여행 정보를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "여행 없음", content = @Content)
    })
    @GetMapping("/{tripId}")
    public ResponseEntity<CustomResponse<TripResponseDto>> getTrip(
            @Parameter(description = "사용자 ID", required = true, example = "1") @RequestParam Long userId,
            @Parameter(description = "여행 ID", required = true, example = "1") @PathVariable Long tripId
    ) {
        TripResponseDto response = tripService.getTrip(userId, tripId);
        return CustomResponseHelper.ok(response);
    }

    @Operation(
            summary = "여행 수정",
            description = "사용자 ID와 여행 ID, 수정할 여행 정보를 입력하여 여행을 수정합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "404", description = "여행 없음", content = @Content)
    })
    @PutMapping("/{tripId}")
    public ResponseEntity<CustomResponse<TripResponseDto>> updateTrip(
            @Parameter(description = "사용자 ID", required = true, example = "1") @RequestParam Long userId,
            @Parameter(description = "여행 ID", required = true, example = "1") @PathVariable Long tripId,
            @Parameter(description = "여행 수정 요청 DTO", required = true) @RequestBody TripRequestDto tripRequestDto
    ) {
        TripResponseDto response = tripService.updateTrip(userId, tripId, tripRequestDto);
        return CustomResponseHelper.ok(response);
    }

    @Operation(
            summary = "여행 삭제",
            description = "사용자 ID와 여행 ID로 여행을 삭제합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "여행 없음", content = @Content)
    })
    @DeleteMapping("/{tripId}")
    public ResponseEntity<Void> deleteTrip(
            @Parameter(description = "사용자 ID", required = true, example = "1") @RequestParam Long userId,
            @Parameter(description = "여행 ID", required = true, example = "1") @PathVariable Long tripId
    ) {
        tripService.deleteTrip(userId, tripId);
        return CustomResponseHelper.noContent();
    }
}
