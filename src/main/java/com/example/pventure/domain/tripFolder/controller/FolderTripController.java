package com.example.pventure.domain.tripFolder.controller;

import com.example.pventure.domain.folder.dto.response.FolderResponseDto;
import com.example.pventure.domain.trip.dto.response.TripResponseDto;
import com.example.pventure.domain.tripFolder.service.TripFolderService;
import com.example.pventure.global.response.CustomResponse;
import com.example.pventure.global.response.CustomResponseHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "FolderTrip", description = "폴더-여행 관련 API")
@RestController
@RequestMapping("/folders/{folderId}/trips")
@RequiredArgsConstructor
public class FolderTripController {

    private final TripFolderService tripFolderService;

    @Operation(summary = "폴더에 여행 추가", description = "폴더 ID와 여행 ID를 전달하여 해당 폴더에 여행을 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "추가 성공",
                    content = @Content(
                            schema = @Schema(implementation = FolderResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "name": "여행 폴더",
                                      "isDefault": false
                                    }
                                    """)
                    )),
            @ApiResponse(responseCode = "404", description = "폴더, 여행 또는 사용자 없음", content = @Content)
    })
    @PostMapping
    public ResponseEntity<CustomResponse<FolderResponseDto>> addTrip(
            @Parameter(description = "사용자 ID", required = true) @RequestParam Long userId,
            @Parameter(description = "폴더 ID", required = true) @PathVariable Long folderId,
            @Parameter(description = "추가할 여행 ID", required = true) @RequestParam Long tripId) {

        return CustomResponseHelper.ok(tripFolderService.addTrip(folderId, tripId, userId));
    }

    @Operation(summary = "폴더 내 여행 조회", description = "폴더 ID로 해당 폴더에 속한 모든 여행을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(
                            schema = @Schema(implementation = TripResponseDto.class),
                            examples = @ExampleObject(value = """
                                    [
                                      {
                                        "id": 1,
                                        "title": "서울 여행",
                                        "startDate": "2025-11-01",
                                        "endDate": "2025-11-05"
                                      },
                                      {
                                        "id": 2,
                                        "title": "부산 여행",
                                        "startDate": "2025-12-01",
                                        "endDate": "2025-12-03"
                                      }
                                    ]
                                    """)
                    )),
            @ApiResponse(responseCode = "404", description = "폴더 또는 사용자 없음", content = @Content)
    })
    @GetMapping
    public ResponseEntity<CustomResponse<List<TripResponseDto>>> getTrips(
            @Parameter(description = "사용자 ID", required = true) @RequestParam Long userId,
            @Parameter(description = "폴더 ID", required = true) @PathVariable Long folderId) {
        return CustomResponseHelper.ok(tripFolderService.getTrips(folderId, userId));
    }

    @Operation(summary = "폴더에서 여행 삭제", description = "폴더 ID와 여행 ID를 전달하여 해당 폴더에서 여행을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "폴더, 여행 또는 사용자 없음", content = @Content)
    })
    @DeleteMapping("/{tripId}")
    public ResponseEntity<Void> deleteTrip(
            @Parameter(description = "사용자 ID", required = true) @RequestParam Long userId,
            @Parameter(description = "폴더 ID", required = true) @PathVariable Long folderId,
            @Parameter(description = "삭제할 여행 ID", required = true) @PathVariable Long tripId) {
        tripFolderService.deleteTrip(folderId, tripId, userId);
        return CustomResponseHelper.noContent();
    }
}
