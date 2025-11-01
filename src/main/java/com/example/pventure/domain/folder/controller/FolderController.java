package com.example.pventure.domain.folder.controller;

import com.example.pventure.domain.folder.dto.request.FolderRequestDto;
import com.example.pventure.domain.folder.dto.response.FolderResponseDto;
import com.example.pventure.domain.folder.service.FolderService;
import com.example.pventure.global.response.CustomResponse;
import com.example.pventure.global.response.CustomResponseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/folders")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    // 폴더 생성
    @PostMapping
    public ResponseEntity<CustomResponse<FolderResponseDto>> createFolder(
            @RequestParam Long userId,
            @RequestBody FolderRequestDto requestDto) {
        return CustomResponseHelper.created(folderService.createFolder(requestDto, userId));
    }

    // 폴더 목록 조회
    @GetMapping
    public ResponseEntity<CustomResponse<List<FolderResponseDto>>> getFolders(
            @RequestParam Long userId) {
        return CustomResponseHelper.ok(folderService.getAllFolders(userId));
    }

    // 폴더 상세 조회
    @GetMapping("/{folderId}")
    public ResponseEntity<CustomResponse<FolderResponseDto>> getFolder(
            @RequestParam Long userId,
            @PathVariable Long folderId) {
        return CustomResponseHelper.ok(folderService.getFolder(folderId, userId));
    }

    // 폴더 수정
    @PutMapping("/{folderId}")
    public ResponseEntity<CustomResponse<FolderResponseDto>> updateFolder(
            @RequestParam Long userId,
            @PathVariable Long folderId,
            @RequestBody FolderRequestDto requestDto) {
        return CustomResponseHelper.ok(folderService.updateFolder(folderId, requestDto, userId));
    }

    // 폴더 삭제
    @DeleteMapping("/{folderId}")
    public ResponseEntity<Void> deleteFolder(
            @RequestParam Long userId,
            @PathVariable Long folderId) {
        folderService.deleteFolder(folderId, userId);
        return CustomResponseHelper.noContent();
    }
}