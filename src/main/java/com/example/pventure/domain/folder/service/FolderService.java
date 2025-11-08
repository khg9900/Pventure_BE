package com.example.pventure.domain.folder.service;

import com.example.pventure.domain.folder.dto.request.FolderRequestDto;
import com.example.pventure.domain.folder.dto.response.FolderResponseDto;
import com.example.pventure.domain.folder.entity.Folder;
import com.example.pventure.domain.user.entity.User;

import java.util.List;

public interface FolderService {

    FolderResponseDto createFolder(FolderRequestDto requestDto, Long userId);

    List<FolderResponseDto> getAllFolders(Long userId);

    FolderResponseDto getFolder(Long folderId, Long userId);

    FolderResponseDto updateFolder(Long folderId, FolderRequestDto requestDto, Long userId);

    void deleteFolder(Long folderId, Long userId);

    Folder getFolderEntity(User user, Long folderId);
}
