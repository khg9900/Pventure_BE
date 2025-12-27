package com.example.pventure.domain.photo.controller;

import com.example.pventure.domain.photo.docs.CreatePhotosDocs;
import com.example.pventure.domain.photo.docs.DeletePhotosDocs;
import com.example.pventure.domain.photo.docs.GenerateUploadUrlsDocs;
import com.example.pventure.domain.photo.docs.GetPhotoDocs;
import com.example.pventure.domain.photo.docs.GetPhotosByAlbumDocs;
import com.example.pventure.domain.photo.docs.GetUnassignedPhotosDocs;
import com.example.pventure.domain.photo.docs.MovePhotosDocs;
import com.example.pventure.domain.photo.dto.request.MovePhotoRequestDto;
import com.example.pventure.domain.photo.dto.request.PhotoRequestDto;
import com.example.pventure.domain.photo.dto.request.UploadUrlRequestDto;
import com.example.pventure.domain.photo.dto.response.PhotoDetailResponseDto;
import com.example.pventure.domain.photo.dto.response.PhotoResponseDto;
import com.example.pventure.domain.photo.dto.response.UploadUrlResponseDto;
import com.example.pventure.domain.photo.service.PhotoService;
import com.example.pventure.global.response.CustomResponse;
import com.example.pventure.global.response.CustomResponseHelper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Photo", description = "사진 관련 API")
@RestController
@RequestMapping("/trips/{tripId}")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    @GenerateUploadUrlsDocs
    @PostMapping("/photos/upload-urls")
    public ResponseEntity<CustomResponse<List<UploadUrlResponseDto>>> generateUploadUrls(
        @RequestParam Long userId,
        @PathVariable Long tripId,
        @Valid @RequestBody List<UploadUrlRequestDto> requestDtos
    ) {
        return CustomResponseHelper.ok(photoService.generateUploadUrls(userId, tripId, requestDtos));
    }

    @CreatePhotosDocs
    @PostMapping("/photos")
    public ResponseEntity<CustomResponse<List<PhotoResponseDto>>> createPhotos(
        @RequestParam Long userId,
        @PathVariable Long tripId,
        @RequestParam(required = false) Long albumId,
        @Valid @RequestBody List<PhotoRequestDto> requestDtos
    ) {
        return CustomResponseHelper.created(photoService.createPhotos(userId, tripId, albumId, requestDtos));
    }

    @GetPhotosByAlbumDocs
    @GetMapping("/albums/{albumId}/photos")
    public ResponseEntity<CustomResponse<List<PhotoResponseDto>>> getPhotosByAlbum(
        @RequestParam Long userId,
        @PathVariable Long tripId,
        @PathVariable Long albumId
    ) {
        return CustomResponseHelper.ok(photoService.getPhotosByAlbum(userId, tripId, albumId));
    }

    @GetUnassignedPhotosDocs
    @GetMapping("/photos/unassigned")
    public ResponseEntity<CustomResponse<List<PhotoResponseDto>>> getUnassignedPhotos(
        @RequestParam Long userId,
        @PathVariable Long tripId
    ) {
        return CustomResponseHelper.ok(photoService.getUnassignedPhotos(userId, tripId));
    }

    @GetPhotoDocs
    @GetMapping("/photos/{photoId}")
    public ResponseEntity<CustomResponse<PhotoDetailResponseDto>> getPhoto(
        @RequestParam Long userId,
        @PathVariable Long tripId,
        @PathVariable Long photoId
    ) {
        return CustomResponseHelper.ok(photoService.getPhoto(userId, tripId, photoId));
    }

    @MovePhotosDocs
    @PutMapping("/photos/move")
    public ResponseEntity<Void> movePhotos(
        @RequestParam Long userId,
        @PathVariable Long tripId,
        @Valid @RequestBody MovePhotoRequestDto requestDto
    ) {
        photoService.movePhotos(userId, tripId, requestDto.getTargetAlbumId(), requestDto.getPhotoIds());
        return CustomResponseHelper.noContent();
    }

    @DeletePhotosDocs
    @DeleteMapping("/photos")
    public ResponseEntity<Void> deletePhotos(
        @RequestParam Long userId,
        @PathVariable Long tripId,
        @RequestParam List<Long> photoIds
    ) {
        photoService.deletePhotos(userId, tripId, photoIds);
        return CustomResponseHelper.noContent();
    }
}
