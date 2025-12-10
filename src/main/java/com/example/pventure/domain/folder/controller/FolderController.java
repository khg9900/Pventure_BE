package com.example.pventure.domain.folder.controller;

import com.example.pventure.domain.folder.docs.*;
import com.example.pventure.domain.folder.dto.request.FolderRequestDto;
import com.example.pventure.domain.folder.dto.response.FolderCountResponseDto;
import com.example.pventure.domain.folder.dto.response.FolderResponseDto;
import com.example.pventure.domain.folder.service.FolderFacade;
import com.example.pventure.global.response.CustomResponse;
import com.example.pventure.global.response.CustomResponseHelper;
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

    private final FolderFacade folderFacade;

    @CreateFolderDocs
    @PostMapping
    public ResponseEntity<CustomResponse<FolderResponseDto>> createFolder(
            @RequestParam Long userId,
            @Valid @RequestBody FolderRequestDto requestDto) {
        return CustomResponseHelper.created(folderFacade.createFolder(requestDto, userId));
    }

    @GetFoldersDocs
    @GetMapping
    public ResponseEntity<CustomResponse<List<FolderCountResponseDto>>> getFolders(
            @RequestParam Long userId) {
        return CustomResponseHelper.ok(folderFacade.getAllFolders(userId));
    }

    @GetFolderDocs
    @GetMapping("/{folderId}")
    public ResponseEntity<CustomResponse<FolderResponseDto>> getFolder(
            @RequestParam Long userId,
            @PathVariable Long folderId) {
        return CustomResponseHelper.ok(folderFacade.getFolder(folderId, userId));
    }

    @UpdateFolderDocs
    @PutMapping("/{folderId}")
    public ResponseEntity<CustomResponse<FolderResponseDto>> updateFolder(
            @RequestParam Long userId,
            @PathVariable Long folderId,
            @Valid @RequestBody FolderRequestDto requestDto) {
        return CustomResponseHelper.ok(folderFacade.updateFolder(folderId, requestDto, userId));
    }

    @DeleteFolderDocs
    @DeleteMapping("/{folderId}")
    public ResponseEntity<Void> deleteFolder(
            @RequestParam Long userId,
            @PathVariable Long folderId) {
        folderFacade.deleteFolder(folderId, userId);
        return CustomResponseHelper.noContent();
    }
}
