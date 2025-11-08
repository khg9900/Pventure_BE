package com.example.pventure.domain.folder.controller;

import com.example.pventure.domain.folder.dto.request.FolderRequestDto;
import com.example.pventure.domain.folder.dto.response.FolderCountResponseDto;
import com.example.pventure.domain.folder.dto.response.FolderResponseDto;
import com.example.pventure.domain.folder.service.FolderService;
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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Folder", description = "폴더 관련 API")
@RestController
@RequestMapping("/folders")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    @Operation(
            summary = "폴더 생성",
            description = "사용자 ID와 폴더 정보를 전달하여 새로운 폴더를 생성합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "생성 성공",
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
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "409", description = "기본 폴더 중복", content = @Content)
    })
    @PostMapping
    public ResponseEntity<CustomResponse<FolderResponseDto>> createFolder(
            @Parameter(description = "폴더를 생성할 사용자 ID", required = true) @RequestParam Long userId,
            @Valid @RequestBody FolderRequestDto requestDto) {
        return CustomResponseHelper.created(folderService.createFolder(requestDto, userId));
    }

    @Operation(
            summary = "폴더 목록 조회",
            description = "사용자 ID로 해당 사용자의 모든 폴더 목록을 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = FolderCountResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "사용자 없음", content = @Content)
    })
    @GetMapping
    public ResponseEntity<CustomResponse<List<FolderCountResponseDto>>> getFolders(
            @Parameter(description = "조회할 사용자의 ID", required = true) @RequestParam Long userId) {
        return CustomResponseHelper.ok(folderService.getAllFolders(userId));
    }

    @Operation(
            summary = "폴더 상세 조회",
            description = "폴더 ID와 사용자 ID로 특정 폴더의 상세 정보를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
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
            @ApiResponse(responseCode = "404", description = "폴더 또는 사용자 없음", content = @Content)
    })
    @GetMapping("/{folderId}")
    public ResponseEntity<CustomResponse<FolderResponseDto>> getFolder(
            @Parameter(description = "조회할 사용자의 ID", required = true) @RequestParam Long userId,
            @Parameter(description = "조회할 폴더의 ID", required = true) @PathVariable Long folderId) {
        return CustomResponseHelper.ok(folderService.getFolder(folderId, userId));
    }

    @Operation(
            summary = "폴더 수정",
            description = "폴더 ID와 사용자 ID를 통해 폴더 정보를 수정합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(
                            schema = @Schema(implementation = FolderResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "name": "업데이트된 폴더",
                                      "isDefault": false
                                    }
                                    """)
                    )),
            @ApiResponse(responseCode = "404", description = "폴더 또는 사용자 없음", content = @Content)
    })
    @PutMapping("/{folderId}")
    public ResponseEntity<CustomResponse<FolderResponseDto>> updateFolder(
            @Parameter(description = "수정할 사용자의 ID", required = true) @RequestParam Long userId,
            @Parameter(description = "수정할 폴더의 ID", required = true) @PathVariable Long folderId,
            @Valid @RequestBody FolderRequestDto requestDto) {
        return CustomResponseHelper.ok(folderService.updateFolder(folderId, requestDto, userId));
    }

    @Operation(
            summary = "폴더 삭제",
            description = "폴더 ID와 사용자 ID를 통해 특정 폴더를 삭제합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "폴더 또는 사용자 없음", content = @Content)
    })
    @DeleteMapping("/{folderId}")
    public ResponseEntity<Void> deleteFolder(
            @Parameter(description = "삭제할 사용자의 ID", required = true) @RequestParam Long userId,
            @Parameter(description = "삭제할 폴더의 ID", required = true) @PathVariable Long folderId) {
        folderService.deleteFolder(folderId, userId);
        return CustomResponseHelper.noContent();
    }
}
