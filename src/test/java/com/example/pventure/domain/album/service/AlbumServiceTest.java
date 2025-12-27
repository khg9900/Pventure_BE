package com.example.pventure.domain.album.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.pventure.domain.album.dto.query.AlbumWithPhotoCountQDto;
import com.example.pventure.domain.album.dto.request.AlbumRequestDto;
import com.example.pventure.domain.album.dto.response.AlbumResponseDto;
import com.example.pventure.domain.album.entity.Album;
import com.example.pventure.domain.album.repository.AlbumRepository;
import com.example.pventure.domain.photo.entity.Photo;
import com.example.pventure.domain.photo.repository.PhotoRepository;
import com.example.pventure.domain.photo.service.PhotoService;
import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.trip.enums.TripStatus;
import com.example.pventure.domain.trip.service.TripPermissionService;
import com.example.pventure.global.exception.ApiException;
import com.example.pventure.global.exception.ErrorCode;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AlbumServiceTest {

    @InjectMocks
    private AlbumServiceImpl albumService;

    @Mock private TripPermissionService tripPermissionService;
    @Mock private AlbumRepository albumRepository;
    @Mock private PhotoService photoService;
    @Mock private PhotoRepository photoRepository;

    private Trip trip;
    private Album album;
    private AlbumRequestDto requestDto;

    @BeforeEach
    void setUp() throws Exception {
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
    }

    private void setId(Object entity, Long id) throws Exception {
        Field idField = entity.getClass().getSuperclass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(entity, id);
    }

    @Test
    @DisplayName("createAlbum 성공")
    void createAlbum_success() {
        // given
        Long userId = 1L;
        Long tripId = 10L;

        requestDto = new AlbumRequestDto("1일차 앨범");

        when(tripPermissionService.getEditableTrip(userId, tripId)).thenReturn(trip);
        when(albumRepository.save(any(Album.class))).thenReturn(album);

        // when
        AlbumResponseDto response = albumService.createAlbum(userId, tripId, requestDto);

        // then
        assertEquals("1일차 앨범", response.getTitle());
        assertEquals(0L, response.getPhotoCount());

        verify(tripPermissionService, times(1)).getEditableTrip(userId, tripId);
        verify(albumRepository, times(1)).save(any(Album.class));
    }

    @Test
    @DisplayName("createAlbum 실패: 편집 권한 없음")
    void createAlbum_fail_permission() {
        // given
        Long userId = 1L;
        Long tripId = 10L;

        requestDto = new AlbumRequestDto("1일차 앨범");

        ApiException permissionEx = new ApiException(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS);
        when(tripPermissionService.getEditableTrip(userId, tripId)).thenThrow(permissionEx);

        // when & then
        ApiException ex = assertThrows(ApiException.class,
            () -> albumService.createAlbum(userId, tripId, requestDto));

        assertEquals(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS, ex.getErrorCode());

        verify(tripPermissionService, times(1)).getEditableTrip(userId, tripId);
        verify(albumRepository, never()).save(any(Album.class));
    }

    @Test
    @DisplayName("getAlbums 성공: 앨범 목록 반환")
    void getAlbums_success() {
        // given
        Long userId = 1L;
        Long tripId = 10L;

        when(tripPermissionService.getViewableTrip(userId, tripId)).thenReturn(trip);

        AlbumWithPhotoCountQDto qDto1 = new AlbumWithPhotoCountQDto(100L, "1일차 앨범", 3L);
        AlbumWithPhotoCountQDto qDto2 = new AlbumWithPhotoCountQDto(101L, "2일차 앨범", 5L);

        when(albumRepository.findAllByTripWithPhotoCount(trip.getId())).thenReturn(List.of(qDto1, qDto2));

        // when
        List<AlbumResponseDto> response = albumService.getAlbums(userId, tripId);

        // then
        assertEquals(2, response.size());
        assertEquals("1일차 앨범", response.get(0).getTitle());
        assertEquals("2일차 앨범", response.get(1).getTitle());
        assertEquals(3L, response.get(0).getPhotoCount());
        assertEquals(5L, response.get(1).getPhotoCount());

        verify(tripPermissionService, times(1)).getViewableTrip(userId, tripId);
        verify(albumRepository, times(1)).findAllByTripWithPhotoCount(trip.getId());
    }

    @Test
    @DisplayName("getAlbums 성공: 앨범이 없을 경우 빈 리스트 반환")
    void getAlbums_empty_success() {
        // given
        Long userId = 1L;
        Long tripId = 10L;

        when(tripPermissionService.getViewableTrip(userId, tripId)).thenReturn(trip);
        when(albumRepository.findAllByTripWithPhotoCount(trip.getId())).thenReturn(Collections.emptyList());

        // when
        List<AlbumResponseDto> response = albumService.getAlbums(userId, tripId);

        // then
        assertNotNull(response);
        assertTrue(response.isEmpty());

        verify(tripPermissionService, times(1)).getViewableTrip(userId, tripId);
        verify(albumRepository, times(1)).findAllByTripWithPhotoCount(trip.getId());
    }

    @Test
    @DisplayName("getAlbums 실패: 조회 권한 없음")
    void getAlbums_fail_permission() {
        // given
        Long userId = 1L;
        Long tripId = 10L;

        ApiException permissionEx = new ApiException(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS);
        when(tripPermissionService.getViewableTrip(userId, tripId)).thenThrow(permissionEx);

        // when & then
        ApiException ex = assertThrows(ApiException.class,
            () -> albumService.getAlbums(userId, tripId));

        assertEquals(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS, ex.getErrorCode());

        verify(tripPermissionService, times(1)).getViewableTrip(userId, tripId);
        verify(albumRepository, never()).findAllByTripWithPhotoCount(anyLong());
    }

    @Test
    @DisplayName("getAlbum 성공")
    void getAlbum_success() {
        // given
        Long userId = 1L;
        Long tripId = 10L;
        Long albumId = 100L;

        when(tripPermissionService.getViewableTrip(userId, tripId)).thenReturn(trip);

        AlbumWithPhotoCountQDto qDto = new AlbumWithPhotoCountQDto(albumId, "1일차 앨범", 3L);

        when(albumRepository.findByIdAndTripWithPhotoCount(albumId, trip.getId())).thenReturn(qDto);

        // when
        AlbumResponseDto response = albumService.getAlbum(userId, tripId, albumId);

        // then
        assertEquals("1일차 앨범", response.getTitle());
        assertEquals(3L, response.getPhotoCount());

        verify(tripPermissionService, times(1)).getViewableTrip(userId, tripId);
        verify(albumRepository, times(1)).findByIdAndTripWithPhotoCount(albumId, trip.getId());
    }

    @Test
    @DisplayName("getAlbum 실패: 없는 앨범")
    void getAlbum_fail_albumNotFound() {
        // given
        Long userId = 1L;
        Long tripId = 10L;
        Long albumId = 999L;

        when(tripPermissionService.getViewableTrip(userId, tripId)).thenReturn(trip);
        when(albumRepository.findByIdAndTripWithPhotoCount(albumId, trip.getId())).thenReturn(null);

        // when & then
        ApiException ex = assertThrows(ApiException.class,
            () -> albumService.getAlbum(userId, tripId, albumId));

        assertEquals(ErrorCode.NOT_FOUND_ALBUM, ex.getErrorCode());

        verify(tripPermissionService, times(1)).getViewableTrip(userId, tripId);
        verify(albumRepository, times(1)).findByIdAndTripWithPhotoCount(albumId, trip.getId());
    }

    @Test
    @DisplayName("updateAlbum 성공")
    void updateAlbum_success() {
        // given
        Long userId = 1L;
        Long tripId = 10L;
        Long albumId = 100L;

        requestDto = new AlbumRequestDto("수정된 앨범");

        when(tripPermissionService.getEditableTrip(userId, tripId)).thenReturn(trip);
        when(albumRepository.findByIdAndTrip(albumId, trip)).thenReturn(Optional.of(album));
        when(photoService.countPhotos(album)).thenReturn(3L);

        // when
        AlbumResponseDto response = albumService.updateAlbum(userId, tripId, albumId, requestDto);

        // then
        assertEquals("수정된 앨범", response.getTitle());
        assertEquals(3L, response.getPhotoCount());

        verify(tripPermissionService, times(1)).getEditableTrip(userId, tripId);
        verify(albumRepository, times(1)).findByIdAndTrip(albumId, trip);
        verify(photoService, times(1)).countPhotos(album);
    }

    @Test
    @DisplayName("updateAlbum 실패: 없는 앨범")
    void updateAlbum_fail_albumNotFound() {
        // given
        Long userId = 1L;
        Long tripId = 10L;
        Long albumId = 999L;

        requestDto = new AlbumRequestDto("수정된 앨범");

        when(tripPermissionService.getEditableTrip(userId, tripId)).thenReturn(trip);
        when(albumRepository.findByIdAndTrip(albumId, trip)).thenReturn(Optional.empty());

        // when & then
        ApiException ex = assertThrows(ApiException.class,
            () -> albumService.updateAlbum(userId, tripId, albumId, requestDto));

        assertEquals(ErrorCode.NOT_FOUND_ALBUM, ex.getErrorCode());

        verify(tripPermissionService, times(1)).getEditableTrip(userId, tripId);
        verify(albumRepository, times(1)).findByIdAndTrip(albumId, trip);
        verify(photoService, never()).countPhotos(any());
    }

    @Test
    @DisplayName("deleteAlbum 성공")
    void deleteAlbum_success() {
        // given
        Long userId = 1L;
        Long tripId = 10L;
        Long albumId = 100L;

        when(tripPermissionService.getEditableTrip(userId, tripId)).thenReturn(trip);
        when(albumRepository.findByIdAndTrip(albumId, trip)).thenReturn(Optional.of(album));

        Photo p1 = mock(Photo.class);
        Photo p2 = mock(Photo.class);

        when(photoRepository.findAllByAlbum(album)).thenReturn(List.of(p1, p2));

        // when
        albumService.deleteAlbum(userId, tripId, albumId);

        // then
        verify(tripPermissionService, times(1)).getEditableTrip(userId, tripId);
        verify(albumRepository, times(1)).findByIdAndTrip(albumId, trip);
        verify(photoRepository, times(1)).findAllByAlbum(album);

        verify(p1, times(1)).removeAlbum();
        verify(p2, times(1)).removeAlbum();

        verify(albumRepository, times(1)).delete(album);
    }

    @Test
    @DisplayName("deleteAlbum 실패: 없는 앨범")
    void deleteAlbum_fail_albumNotFound() {
        // given
        Long userId = 1L;
        Long tripId = 10L;
        Long albumId = 999L;

        when(tripPermissionService.getEditableTrip(userId, tripId)).thenReturn(trip);
        when(albumRepository.findByIdAndTrip(albumId, trip)).thenReturn(Optional.empty());

        // when & then
        ApiException ex = assertThrows(ApiException.class,
            () -> albumService.deleteAlbum(userId, tripId, albumId));

        assertEquals(ErrorCode.NOT_FOUND_ALBUM, ex.getErrorCode());

        verify(tripPermissionService, times(1)).getEditableTrip(userId, tripId);
        verify(albumRepository, times(1)).findByIdAndTrip(albumId, trip);
        verify(photoRepository, never()).findAllByAlbum(any(Album.class));
        verify(albumRepository, never()).delete(any(Album.class));
    }
}