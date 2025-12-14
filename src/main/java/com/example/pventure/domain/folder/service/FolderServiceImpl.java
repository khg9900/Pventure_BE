package com.example.pventure.domain.folder.service;

import com.example.pventure.domain.folder.dto.request.FolderRequestDto;
import com.example.pventure.domain.folder.dto.response.FolderCountResponseDto;
import com.example.pventure.domain.folder.dto.response.FolderResponseDto;
import com.example.pventure.domain.folder.entity.Folder;
import com.example.pventure.domain.folder.repository.FolderRepository;
import com.example.pventure.domain.tripFolder.service.TripFolderService;
import com.example.pventure.domain.user.entity.User;
import com.example.pventure.domain.user.repository.UserRepository;
import com.example.pventure.global.exception.ApiException;
import com.example.pventure.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {

    private final UserRepository userRepository;
    private final FolderRepository folderRepository;
    private final TripFolderService tripFolderService;

    // ------------------ CREATE ------------------
    @Override
    public FolderResponseDto createFolder(FolderRequestDto requestDto, Long userId) {
        User user = loadUser(userId);

        Folder folder = folderRepository.save(requestDto.toEntity(user));
        return FolderResponseDto.from(folder);
    }

    // ------------------ READ ------------------
    @Override
    @Transactional(readOnly = true)
    public List<FolderCountResponseDto> getAllFolders(Long userId) {
        User user = loadUser(userId);

        return folderRepository.findAllByUser(user).stream()
                .map(folder -> {
                    Long tripCount = tripFolderService.countTrips(folder);
                    return FolderCountResponseDto.from(folder, tripCount);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public FolderResponseDto getFolder(Long folderId, Long userId) {
        User user = loadUser(userId);
        Folder folder = loadFolder(folderId, user);
        return FolderResponseDto.from(folder);
    }

    // ------------------ UPDATE ------------------
    @Override
    public FolderResponseDto updateFolder(Long folderId, FolderRequestDto requestDto, Long userId) {
        User user = loadUser(userId);
        Folder folder = loadFolder(folderId, user);

        folder.updateFolderName(requestDto.getName());
        return FolderResponseDto.from(folder);
    }

    // ------------------ DELETE ------------------
    @Override
    public void deleteFolder(Long folderId, Long userId) {
        User user = loadUser(userId);
        Folder folder = loadFolder(folderId, user);

        folderRepository.delete(folder);
    }

    // ------------------ HELPER ------------------
    private User loadUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
    }

    private Folder loadFolder(Long folderId, User user) {
        return folderRepository.findByIdAndUser(folderId, user)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_FOLDER));
    }

    @Override
    public Folder getFolderEntity(User user, Long folderId) {
        return folderRepository.findWithTrips(folderId, user)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_FOLDER));
    }
}
