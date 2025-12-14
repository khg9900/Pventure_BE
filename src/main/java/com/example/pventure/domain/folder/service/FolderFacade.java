package com.example.pventure.domain.folder.service;

import com.example.pventure.domain.folder.dto.request.FolderRequestDto;
import com.example.pventure.domain.folder.dto.response.FolderCountResponseDto;
import com.example.pventure.domain.folder.dto.response.FolderResponseDto;

import java.util.List;

public interface FolderFacade {

    FolderResponseDto createFolder(FolderRequestDto requestDto, Long userId);

    List<FolderCountResponseDto> getAllFolders(Long userId);

    FolderResponseDto getFolder(Long folderId, Long userId);

    FolderResponseDto updateFolder(Long folderId, FolderRequestDto requestDto, Long userId);

    void deleteFolder(Long folderId, Long userId);
}
