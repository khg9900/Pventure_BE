package com.example.pventure.domain.folder.service;

import com.example.pventure.domain.folder.dto.request.FolderRequestDto;
import com.example.pventure.domain.folder.dto.response.FolderResponseDto;
import com.example.pventure.domain.folder.entity.Folder;
import com.example.pventure.domain.folder.repository.FolderRepository;
import com.example.pventure.domain.user.entity.User;
import com.example.pventure.domain.user.repository.UserRepository;
import com.example.pventure.global.exception.ApiException;
import com.example.pventure.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {

    private final UserRepository userRepository;
    private final FolderRepository folderRepository;

    @Override
    public FolderResponseDto createFolder(FolderRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));

        Folder folder = requestDto.toEntity(user);
        folderRepository.save(folder);

        return FolderResponseDto.from(folder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FolderResponseDto> getAllFolders(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));

        return folderRepository.findFolderByUser(user)
                .stream()
                .map(FolderResponseDto::from)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public FolderResponseDto getFolder(Long folderId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));

        Folder folder = folderRepository.findByIdAndUser(folderId, user)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_FOLDER));

        return FolderResponseDto.from(folder);
    }

    @Override
    public FolderResponseDto updateFolder(Long folderId, FolderRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));

        Folder folder = folderRepository.findByIdAndUser(folderId, user)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_FOLDER));

        folder.updateFolderName(requestDto.getName());

        return FolderResponseDto.from(folder);
    }

    @Override
    public void deleteFolder(Long folderId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));

        Folder folder = folderRepository.findWithTrips(folderId, user)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_FOLDER));

        if (folder.isDefault()) {
            throw new ApiException(ErrorCode.CANNOT_DELETE_DEFAULT_FOLDER);
        }

        Folder defaultFolder = folderRepository.findDefaultFolderByUser(user)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_FOLDER));

        // 삭제할 폴더에 속한 Trip들을 기본 폴더로 이동
        folder.getTripFolders().forEach(tripFolder -> {
            try {
                tripFolder.updateFolder(defaultFolder);
            } catch (DataIntegrityViolationException e) {
                // 중복 발생 → 속으로 삼키고 넘어감
                log.warn("TripFolder 중복 발생, 안전하게 무시합니다. tripId={}, folderId={}",
                        tripFolder.getTrip().getId(), defaultFolder.getId());
            }
        });

        folderRepository.delete(folder);
    }


    @Override
    public Folder getFolderEntity(User user, Long folderId) {
        return folderRepository.findWithTrips(folderId, user)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_FOLDER));
    }
}
