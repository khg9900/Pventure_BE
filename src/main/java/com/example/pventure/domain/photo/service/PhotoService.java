package com.example.pventure.domain.photo.service;

import com.example.pventure.domain.album.entity.Album;
import com.example.pventure.domain.photo.dto.request.PhotoRequestDto;
import com.example.pventure.domain.photo.dto.request.UploadUrlRequestDto;
import com.example.pventure.domain.photo.dto.response.PhotoDetailResponseDto;
import com.example.pventure.domain.photo.dto.response.PhotoResponseDto;
import com.example.pventure.domain.photo.dto.response.UploadUrlResponseDto;
import java.util.List;

public interface PhotoService {

    List<UploadUrlResponseDto> generateUploadUrls(Long userId, Long tripId, List<UploadUrlRequestDto> requestDtos);

    List<PhotoResponseDto> createPhotos(Long userId, Long tripId, Long albumId, List<PhotoRequestDto> requestDtos);

    List<PhotoResponseDto> getPhotosByAlbum(Long userId, Long tripId, Long albumId);

    List<PhotoResponseDto> getUnassignedPhotos(Long userId, Long tripId);

    PhotoDetailResponseDto getPhoto(Long userId, Long tripId, Long photoId);

    void movePhotos(Long userId, Long tripId, Long targetAlbumId, List<Long> albumIds);

    void deletePhotos(Long userId, Long tripId, List<Long> photoIds);

    Long countPhotos(Album album);
}
