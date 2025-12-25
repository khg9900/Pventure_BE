package com.example.pventure.domain.photo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.pventure.domain.album.entity.Album;
import com.example.pventure.domain.album.repository.AlbumRepository;
import com.example.pventure.domain.photo.dto.request.PhotoRequestDto;
import com.example.pventure.domain.photo.dto.response.PhotoDetailResponseDto;
import com.example.pventure.domain.photo.dto.response.PhotoResponseDto;
import com.example.pventure.domain.photo.entity.Photo;
import com.example.pventure.domain.photo.repository.PhotoRepository;
import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.trip.enums.TripStatus;
import com.example.pventure.domain.trip.repository.TripRepository;
import com.example.pventure.domain.trip.service.TripPermissionService;
import com.example.pventure.domain.user.entity.User;
import com.example.pventure.domain.user.enums.SocialProvider;
import com.example.pventure.domain.user.repository.UserRepository;
import com.example.pventure.global.exception.ApiException;
import com.example.pventure.global.exception.ErrorCode;
import com.example.pventure.global.s3.S3Service;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PhotoServiceTest {

    @InjectMocks
    private PhotoServiceImpl photoService;

    @Mock private S3Service s3Service;
    @Mock private TripPermissionService tripPermissionService;
    @Mock private UserRepository userRepository;
    @Mock private TripRepository tripRepository;
    @Mock private AlbumRepository albumRepository;
    @Mock private PhotoRepository photoRepository;

    private User user;
    private Trip trip;
    private Album album;
    private Photo photo;

    @BeforeEach
    void setUp() throws Exception {
        user = User.builder()
            .name("홍길동")
            .email("hong@example.com")
            .socialProvider(SocialProvider.GOOGLE)
            .providerId("google-id-123")
            .imageUrl("https://example.com/image.jpg")
            .build();
        setId(user, 1L);

        trip = Trip.builder()
            .title("제주 여행")
            .status(TripStatus.COMPLETED)
            .startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(3))
            .build();
        setId(trip, 10L);

        album = Album.builder()
            .title("1일차 앨범")
            .trip(trip)
            .build();
        setId(album, 100L);

        photo = Photo.builder()
            .trip(trip)
            .album(album)
            .uploader(user)
            .originalFileName("IMG_01.JPG")
            .contentType("image/jpeg")
            .fileSize(345678L)
            .s3Key("trips/10/photos/uuid_IMG_01.JPG")
            .build();
        setId(photo, 1000L);
    }

    private void setId(Object entity, Long id) throws Exception {
        Field idField = entity.getClass().getSuperclass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(entity, id);
    }

    @Test
    @DisplayName("createPhotos 성공: albumId가 있는 경우")
    void createPhotos_success_withAlbum() {
        // given
        Long userId = 1L;
        Long tripId = 10L;
        Long albumId = 100L;

        PhotoRequestDto requestDto = mock(PhotoRequestDto.class);
        Photo photo = mock(Photo.class);
        Photo savedPhoto = mock(Photo.class);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(tripRepository.findById(tripId)).thenReturn(Optional.of(trip));
        when(albumRepository.findByIdAndTrip(albumId, trip)).thenReturn(Optional.of(album));

        when(requestDto.toEntity(trip, album, user)).thenReturn(photo);
        when(photoRepository.save(photo)).thenReturn(savedPhoto);

        when(savedPhoto.getId()).thenReturn(1000L);
        when(savedPhoto.getS3Key()).thenReturn("s3/key/1000");
        when(s3Service.createDownloadUrl("s3/key/1000")).thenReturn("download-url-1000");

        // when
        List<PhotoResponseDto> response = photoService.createPhotos(userId, tripId, albumId, List.of(requestDto));

        // then
        assertEquals(1, response.size());
        assertEquals(1000L, response.get(0).getId());
        assertEquals("download-url-1000", response.get(0).getDownloadUrl());

        verify(tripPermissionService, times(1)).checkEditableTrip(user, trip);
        verify(albumRepository, times(1)).findByIdAndTrip(albumId, trip);
        verify(photoRepository, times(1)).save(photo);
        verify(s3Service, times(1)).createDownloadUrl("s3/key/1000");
    }

    @Test
    @DisplayName("createPhotos 성공: albumId가 null인 경우")
    void createPhotos_success_withoutAlbum() {
        // given
        Long userId = 1L;
        Long tripId = 10L;

        PhotoRequestDto requestDto = mock(PhotoRequestDto.class);
        Photo photo = mock(Photo.class);
        Photo savedPhoto = mock(Photo.class);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(tripRepository.findById(tripId)).thenReturn(Optional.of(trip));

        when(requestDto.toEntity(trip, null, user)).thenReturn(photo);
        when(photoRepository.save(photo)).thenReturn(savedPhoto);

        when(savedPhoto.getId()).thenReturn(1000L);
        when(savedPhoto.getS3Key()).thenReturn("s3/key/1000");
        when(s3Service.createDownloadUrl("s3/key/1000")).thenReturn("download-url-1000");

        // when
        List<PhotoResponseDto> response = photoService.createPhotos(userId, tripId, null, List.of(requestDto));

        // then
        assertEquals(1, response.size());
        assertEquals(1000L, response.get(0).getId());
        assertEquals("download-url-1000", response.get(0).getDownloadUrl());

        verify(tripPermissionService, times(1)).checkEditableTrip(user, trip);
        verify(albumRepository, never()).findByIdAndTrip(anyLong(), any(Trip.class));
        verify(photoRepository, times(1)).save(photo);
        verify(s3Service, times(1)).createDownloadUrl("s3/key/1000");
    }

    @Test
    @DisplayName("createPhotos 실패: 없는 앨범")
    void createPhoto_fail_albumNotFound() {
        // given
        Long userId = 1L;
        Long tripId = 10L;
        Long albumId = 999L;

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(tripRepository.findById(tripId)).thenReturn(Optional.of(trip));
        when(albumRepository.findByIdAndTrip(albumId, trip)).thenReturn(Optional.empty());

        // when & then
        ApiException ex = assertThrows(ApiException.class,
            () -> photoService.createPhotos(userId, tripId, albumId, List.of()));

        assertEquals(ErrorCode.NOT_FOUND_ALBUM, ex.getErrorCode());

        verify(tripPermissionService, times(1)).checkEditableTrip(user, trip);
        verify(photoRepository, never()).save(any());
        verify(s3Service, never()).createDownloadUrl(anyString());
    }

    @Test
    @DisplayName("getPhotosByAlbum 성공")
    void getPhotosByAlbum_success() {
        // given
        Long userId = 1L;
        Long tripId = 10L;
        Long albumId = 100L;

        Photo p1 = mock(Photo.class);
        Photo p2 = mock(Photo.class);

        when(tripPermissionService.getViewableTrip(userId, tripId)).thenReturn(trip);
        when(albumRepository.findByIdAndTrip(albumId, trip)).thenReturn(Optional.of(album));
        when(photoRepository.findAllByAlbum(album)).thenReturn(List.of(p1, p2));

        when(p1.getId()).thenReturn(1000L);
        when(p2.getId()).thenReturn(1001L);

        when(p1.getS3Key()).thenReturn("s3/key/1");
        when(p2.getS3Key()).thenReturn("s3/key/2");

        when(s3Service.createDownloadUrl(p1.getS3Key())).thenReturn("download-url-1");
        when(s3Service.createDownloadUrl(p2.getS3Key())).thenReturn("download-url-2");

        // when
        List<PhotoResponseDto> response = photoService.getPhotosByAlbum(userId, tripId, albumId);

        // then
        assertEquals(2, response.size());
        assertEquals(1000L, response.get(0).getId());

        verify(tripPermissionService, times(1)).getViewableTrip(userId, tripId);
        verify(albumRepository, times(1)).findByIdAndTrip(albumId, trip);
        verify(photoRepository, times(1)).findAllByAlbum(album);
        verify(s3Service, times(1)).createDownloadUrl("s3/key/1");
        verify(s3Service, times(1)).createDownloadUrl("s3/key/2");
    }

    @Test
    @DisplayName("getPhotosByAlbum 실패: 조회 권한 없음")
    void getPhotosByAlbum_fail_permission() {
        // given
        Long userId = 1L;
        Long tripId = 10L;
        Long albumId = 100L;

        ApiException permissionEx = new ApiException(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS);
        when(tripPermissionService.getViewableTrip(userId, tripId)).thenThrow(permissionEx);

        // when & then
        ApiException ex = assertThrows(ApiException.class,
            () -> photoService.getPhotosByAlbum(userId, tripId, albumId));

        assertEquals(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS, ex.getErrorCode());

        verify(tripPermissionService, times(1)).getViewableTrip(userId, tripId);
        verify(albumRepository, never()).findByIdAndTrip(anyLong(), any(Trip.class));
        verify(photoRepository, never()).findAllByAlbum(any(Album.class));
        verify(s3Service, never()).createDownloadUrl(anyString());
    }

    @Test
    @DisplayName("getPhotosByAlbum 실패: 없는 앨범")
    void getPhotosByAlbum_fail_albumNotFound() {
        // given
        Long userId = 1L;
        Long tripId = 10L;
        Long albumId = 999L;

        when(tripPermissionService.getViewableTrip(userId, tripId)).thenReturn(trip);
        when(albumRepository.findByIdAndTrip(albumId, trip)).thenReturn(Optional.empty());

        // when & then
        ApiException ex = assertThrows(ApiException.class,
            () -> photoService.getPhotosByAlbum(userId, tripId, albumId));

        assertEquals(ErrorCode.NOT_FOUND_ALBUM, ex.getErrorCode());

        verify(tripPermissionService, times(1)).getViewableTrip(userId, tripId);
        verify(albumRepository, times(1)).findByIdAndTrip(albumId, trip);
        verify(photoRepository, never()).findAllByAlbum(any(Album.class));
        verify(s3Service, never()).createDownloadUrl(anyString());
    }

    @Test
    @DisplayName("getUnassignedPhotos 성공")
    void getUnassignedPhotos_success() {
        // given
        Long userId = 1L;
        Long tripId = 10L;

        photo.removeAlbum();

        when(tripPermissionService.getViewableTrip(userId, tripId)).thenReturn(trip);
        when(photoRepository.findAllByTripAndAlbumIsNull(trip)).thenReturn(List.of(photo));
        when(s3Service.createDownloadUrl(photo.getS3Key())).thenReturn("download-url-1");

        // when
        List<PhotoResponseDto> response = photoService.getUnassignedPhotos(userId, tripId);

        // then
        assertEquals(1, response.size());
        assertEquals(1000L, response.get(0).getId());

        verify(tripPermissionService, times(1)).getViewableTrip(userId, tripId);
        verify(photoRepository, times(1)).findAllByTripAndAlbumIsNull(trip);
    }

    @Test
    @DisplayName("getUnassignedPhotos 실패: 조회 권한 없음")
    void getUnassignedPhotos_fail_permission() {
        // given
        Long userId = 1L;
        Long tripId = 10L;

        ApiException permissionEx = new ApiException(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS);
        when(tripPermissionService.getViewableTrip(userId, tripId)).thenThrow(permissionEx);

        // when & then
        ApiException ex = assertThrows(ApiException.class,
            () -> photoService.getUnassignedPhotos(userId, tripId));

        assertEquals(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS, ex.getErrorCode());

        verify(tripPermissionService, times(1)).getViewableTrip(userId, tripId);
        verify(photoRepository, never()).findAllByTripAndAlbumIsNull(any(Trip.class));
        verify(s3Service, never()).createDownloadUrl(anyString());
    }

    @Test
    @DisplayName("getPhoto 성공")
    void getPhoto_success() {
        // given
        Long userId = 1L;
        Long tripId = 10L;
        Long photoId = 1000L;

        when(tripPermissionService.getViewableTrip(userId, tripId)).thenReturn(trip);
        when(photoRepository.findByIdAndTrip(photoId, trip)).thenReturn(Optional.of(photo));
        when(s3Service.createDownloadUrl(photo.getS3Key())).thenReturn("download-url-1");

        // when
        PhotoDetailResponseDto response = photoService.getPhoto(userId, tripId, photoId);

        // then
        assertEquals(1000L, response.getId());
        assertEquals("download-url-1", response.getDownloadUrl());
        assertEquals("홍길동", response.getUploaderName());

        verify(tripPermissionService, times(1)).getViewableTrip(userId, tripId);
        verify(photoRepository, times(1)).findByIdAndTrip(photoId, trip);
        verify(s3Service, times(1)).createDownloadUrl(photo.getS3Key());
    }

    @Test
    @DisplayName("getPhoto 실패: 없는 사진")
    void getPhoto_fail_photoNotFound() {
        // given
        Long userId = 1L;
        Long tripId = 10L;
        Long photoId = 9999L;

        when(tripPermissionService.getViewableTrip(userId, tripId)).thenReturn(trip);
        when(photoRepository.findByIdAndTrip(photoId, trip)).thenReturn(Optional.empty());

        // when & then
        ApiException ex = assertThrows(ApiException.class,
            () -> photoService.getPhoto(userId, tripId, photoId));

        assertEquals(ErrorCode.NOT_FOUND_PHOTO, ex.getErrorCode());

        verify(tripPermissionService, times(1)).getViewableTrip(userId, tripId);
        verify(photoRepository, times(1)).findByIdAndTrip(photoId, trip);
        verify(s3Service, never()).createDownloadUrl(anyString());
    }

    @Test
    @DisplayName("movePhotos 성공")
    void movePhotos_success() {
        // given
        Long userId = 1L;
        Long tripId = 10L;
        Long targetAlbumId = 100L;
        List<Long> photoIds = List.of(1000L);
        Set<Long> uniqueIds = new HashSet<>(photoIds);

        when(tripPermissionService.getEditableTrip(userId, tripId)).thenReturn(trip);
        when(albumRepository.findByIdAndTrip(targetAlbumId, trip)).thenReturn(Optional.of(album));
        when(photoRepository.findAllByIdInAndTrip(eq(uniqueIds), eq(trip))).thenReturn(List.of(photo));

        // when
        photoService.movePhotos(userId, tripId, targetAlbumId, photoIds);

        // then
        verify(tripPermissionService, times(1)).getEditableTrip(userId, tripId);
        verify(albumRepository, times(1)).findByIdAndTrip(targetAlbumId, trip);
        verify(photoRepository, times(1)).findAllByIdInAndTrip(eq(uniqueIds), eq(trip));
    }

    @Test
    @DisplayName("movePhotos 성공: photoIds에 중복이 있어도 정상 처리")
    void movePhotos_success_withDuplicateIds() {
        // given
        Long userId = 1L;
        Long tripId = 10L;
        Long targetAlbumId = 100L;
        List<Long> photoIds = List.of(1000L, 1000L);
        Set<Long> uniqueIds = new HashSet<>(photoIds);

        when(tripPermissionService.getEditableTrip(userId, tripId)).thenReturn(trip);
        when(albumRepository.findByIdAndTrip(targetAlbumId, trip)).thenReturn(Optional.of(album));
        when(photoRepository.findAllByIdInAndTrip(eq(uniqueIds), eq(trip))).thenReturn(List.of(photo));

        // when
        photoService.movePhotos(userId, tripId, targetAlbumId, photoIds);

        // then
        verify(tripPermissionService, times(1)).getEditableTrip(userId, tripId);
        verify(albumRepository, times(1)).findByIdAndTrip(targetAlbumId, trip);
        verify(photoRepository, times(1)).findAllByIdInAndTrip(eq(uniqueIds), eq(trip));
    }

    @Test
    @DisplayName("movePhotos 실패: 사진 개수 불일치")
    void movePhotos_fail_photoNotFound() {
        // given
        Long userId = 1L;
        Long tripId = 10L;
        Long targetAlbumId = 100L;
        List<Long> photoIds = List.of(1000L, 1001L, 1002L);
        Set<Long> uniqueIds = new HashSet<>(photoIds);

        when(tripPermissionService.getEditableTrip(userId, tripId)).thenReturn(trip);
        when(albumRepository.findByIdAndTrip(targetAlbumId, trip)).thenReturn(Optional.of(album));
        when(photoRepository.findAllByIdInAndTrip(eq(uniqueIds), eq(trip))).thenReturn(List.of(photo));

        // when & then
        ApiException ex = assertThrows(ApiException.class,
            () -> photoService.movePhotos(userId, tripId, targetAlbumId, photoIds));

        assertEquals(ErrorCode.NOT_FOUND_PHOTO, ex.getErrorCode());

        verify(tripPermissionService, times(1)).getEditableTrip(userId, tripId);
        verify(albumRepository, times(1)).findByIdAndTrip(targetAlbumId, trip);
        verify(photoRepository, times(1)).findAllByIdInAndTrip(eq(uniqueIds), eq(trip));
    }

    @Test
    @DisplayName("deletePhotos 성공")
    void deletePhotos_success() {
        // given
        Long userId = 1L;
        Long tripId = 10L;
        List<Long> photoIds = List.of(1000L);
        Set<Long> uniqueIds = new HashSet<>(photoIds);

        when(tripPermissionService.getEditableTrip(userId, tripId)).thenReturn(trip);
        when(photoRepository.findAllByIdInAndTrip(eq(uniqueIds), eq(trip))).thenReturn(List.of(photo));

        // when
        photoService.deletePhotos(userId, tripId, photoIds);

        // then
        verify(tripPermissionService, times(1)).getEditableTrip(userId, tripId);
        verify(photoRepository, times(1)).findAllByIdInAndTrip(eq(uniqueIds), eq(trip));
        verify(s3Service, times(1)).deleteFile(photo.getS3Key());
        verify(photoRepository, times(1)).deleteAll(List.of(photo));
    }

    @Test
    @DisplayName("deletePhotos 실패: 사진 개수 불일치")
    void deletePhotos_fail_photoNotFound() {
        // given
        Long userId = 1L;
        Long tripId = 10L;
        List<Long> photoIds = List.of(1000L, 10001L, 10002L);
        Set<Long> uniqueIds = new HashSet<>(photoIds);

        when(tripPermissionService.getEditableTrip(userId, tripId)).thenReturn(trip);
        when(photoRepository.findAllByIdInAndTrip(eq(uniqueIds), eq(trip))).thenReturn(List.of(photo));

        // when & then
        ApiException ex = assertThrows(ApiException.class,
            () -> photoService.deletePhotos(userId, tripId, photoIds));

        assertEquals(ErrorCode.NOT_FOUND_PHOTO, ex.getErrorCode());

        verify(tripPermissionService, times(1)).getEditableTrip(userId, tripId);
        verify(photoRepository, times(1)).findAllByIdInAndTrip(eq(uniqueIds), eq(trip));
        verify(s3Service, never()).deleteFile(anyString());
        verify(photoRepository, never()).deleteAll(any());
    }
}