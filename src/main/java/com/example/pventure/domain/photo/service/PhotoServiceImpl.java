package com.example.pventure.domain.photo.service;

import com.example.pventure.domain.album.entity.Album;
import com.example.pventure.domain.album.repository.AlbumRepository;
import com.example.pventure.domain.photo.dto.request.PhotoRequestDto;
import com.example.pventure.domain.photo.dto.request.UploadUrlRequestDto;
import com.example.pventure.domain.photo.dto.response.PhotoDetailResponseDto;
import com.example.pventure.domain.photo.dto.response.PhotoResponseDto;
import com.example.pventure.domain.photo.dto.response.UploadUrlResponseDto;
import com.example.pventure.domain.photo.entity.Photo;
import com.example.pventure.domain.photo.repository.PhotoRepository;
import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.trip.repository.TripRepository;
import com.example.pventure.domain.trip.service.TripPermissionService;
import com.example.pventure.domain.user.entity.User;
import com.example.pventure.domain.user.repository.UserRepository;
import com.example.pventure.global.exception.ApiException;
import com.example.pventure.global.exception.ErrorCode;
import com.example.pventure.global.s3.S3KeyGenerator;
import com.example.pventure.global.s3.S3Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    private final S3Service s3Service;
    private final TripPermissionService tripPermissionService;
    private final S3KeyGenerator s3KeyGenerator;
    private final UserRepository userRepository;
    private final TripRepository tripRepository;
    private final AlbumRepository albumRepository;
    private final PhotoRepository photoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UploadUrlResponseDto> generateUploadUrls(Long userId, Long tripId, List<UploadUrlRequestDto> requestDtos) {

        Trip trip = tripPermissionService.getEditableTrip(userId, tripId);

        return requestDtos.stream()
            .map(requestDto -> {
                String s3Key = s3KeyGenerator.generatePhotoKey(trip.getId(), requestDto.getOriginalFileName());
                String uploadUrl = s3Service.createUploadUrl(s3Key);

                return UploadUrlResponseDto.builder()
                    .s3Key(s3Key)
                    .uploadUrl(uploadUrl)
                    .originalFileName(requestDto.getOriginalFileName())
                    .build();
            })
            .toList();
    }

    @Override
    public List<PhotoResponseDto> createPhotos(Long userId, Long tripId, Long albumId, List<PhotoRequestDto> requestDtos) {

        User user = loadUser(userId);
        Trip trip = loadTrip(tripId);

        tripPermissionService.checkEditableTrip(user, trip);

        Album album;

        if (albumId != null) {
            album = loadAlbum(albumId, trip);
        } else {
            album = null;
        }

        return requestDtos.stream()
            .map(requestDto -> {

                Photo photo = requestDto.toEntity(trip, album, user);
                Photo savedPhoto = photoRepository.save(photo);

                String downloadUrl = s3Service.createDownloadUrl(savedPhoto.getS3Key());

                return PhotoResponseDto.from(savedPhoto, downloadUrl);
            })
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PhotoResponseDto> getPhotosByAlbum(Long userId, Long tripId, Long albumId) {

        Trip trip = tripPermissionService.getViewableTrip(userId, tripId);

        Album album = loadAlbum(albumId, trip);

        List<Photo> photos = photoRepository.findAllByAlbum(album);

        return photos.stream()
            .map(photo -> {
                String downloadUrl = s3Service.createDownloadUrl(photo.getS3Key());
                return PhotoResponseDto.from(photo, downloadUrl);
            })
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PhotoResponseDto> getUnassignedPhotos(Long userId, Long tripId) {

        Trip trip = tripPermissionService.getViewableTrip(userId, tripId);

        List<Photo> photos = photoRepository.findAllByTripAndAlbumIsNull(trip);

        return photos.stream()
            .map(photo -> {
                String downloadUrl = s3Service.createDownloadUrl(photo.getS3Key());
                return PhotoResponseDto.from(photo, downloadUrl);
            })
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PhotoDetailResponseDto getPhoto(Long userId, Long tripId, Long photoId) {

        Trip trip = tripPermissionService.getViewableTrip(userId, tripId);

        Photo photo = photoRepository.findByIdAndTrip(photoId, trip)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_PHOTO));

        String downloadUrl = s3Service.createDownloadUrl(photo.getS3Key());

        return PhotoDetailResponseDto.from(photo, downloadUrl);
    }

    @Override
    public void movePhotos(Long userId, Long tripId, Long targetAlbumId, List<Long> photoIds) {

        if (photoIds == null || photoIds.isEmpty()) {
            return;
        }

        Trip trip = tripPermissionService.getEditableTrip(userId, tripId);

        Album album;

        if (targetAlbumId != null) {
            album = loadAlbum(targetAlbumId, trip);
        } else {
            album = null;
        }

        List<Photo> photos = loadPhotos(photoIds, trip);
        photos.forEach(photo -> photo.updateAlbum(album));
    }

    @Override
    public void deletePhotos(Long userId, Long tripId, List<Long> photoIds) {

        if (photoIds == null || photoIds.isEmpty()) {
            return;
        }

        Trip trip = tripPermissionService.getEditableTrip(userId, tripId);

        List<Photo> photos = loadPhotos(photoIds, trip);
        photos.forEach(photo -> s3Service.deleteFile(photo.getS3Key()));

        photoRepository.deleteAll(photos);
    }

    @Override
    public Long countPhotos(Album album) {
        return photoRepository.countByAlbum(album);
    }

    private User loadUser(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
    }

    private Trip loadTrip(Long tripId) {
        return tripRepository.findById(tripId)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TRIP));
    }

    private Album loadAlbum(Long albumId, Trip trip) {
        return albumRepository.findByIdAndTrip(albumId, trip)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_ALBUM));
    }

    private List<Photo> loadPhotos(List<Long> photoIds, Trip trip) {
        Set<Long> uniqueIds = new HashSet<>(photoIds);
        List<Photo> photos = photoRepository.findAllByIdInAndTrip(uniqueIds, trip);

        if (photos.size() != uniqueIds.size()) {
            throw new ApiException(ErrorCode.NOT_FOUND_PHOTO);
        }
        return photos;
    }
}
