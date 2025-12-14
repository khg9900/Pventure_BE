package com.example.pventure.domain.folder.service;

import com.example.pventure.domain.folder.dto.request.FolderRequestDto;
import com.example.pventure.domain.folder.dto.response.FolderCountResponseDto;
import com.example.pventure.domain.folder.dto.response.FolderResponseDto;
import com.example.pventure.domain.trip.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderFacadeImpl implements FolderFacade {

    private final FolderService folderService;
    private final TripService tripService;

    private static final Long ALL_FOLDER_ID = 0L;
    private static final Long UNSPECIFIED_FOLDER_ID = -1L;

    private static final String ALL_FOLDER_NAME = "전체 폴더";
    private static final String UNSPECIFIED_FOLDER_NAME = "미지정 폴더";

    @Override
    public FolderResponseDto createFolder(FolderRequestDto requestDto, Long userId) {
        return folderService.createFolder(requestDto, userId);
    }

    @Override
    public List<FolderCountResponseDto> getAllFolders(Long userId) {

        Long totalTripCount = tripService.countTrip(userId);
        Long unspecifiedDateTripCount = tripService.countTripsWithNoDate(userId);

        List<FolderCountResponseDto> userFolders = folderService.getAllFolders(userId);

        List<FolderCountResponseDto> result = new ArrayList<>();

        result.add(new FolderCountResponseDto(ALL_FOLDER_ID, ALL_FOLDER_NAME, totalTripCount));
        result.add(new FolderCountResponseDto(UNSPECIFIED_FOLDER_ID, UNSPECIFIED_FOLDER_NAME, unspecifiedDateTripCount));

        result.addAll(userFolders);

        return result;
    }

    @Override
    public FolderResponseDto getFolder(Long folderId, Long userId) {
        return folderService.getFolder(folderId, userId);
    }

    @Override
    public FolderResponseDto updateFolder(Long folderId, FolderRequestDto requestDto, Long userId) {
        return folderService.updateFolder(folderId, requestDto, userId);
    }

    @Override
    public void deleteFolder(Long folderId, Long userId) {
        folderService.deleteFolder(folderId, userId);
    }
}
