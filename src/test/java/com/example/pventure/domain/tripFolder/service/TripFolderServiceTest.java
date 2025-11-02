package com.example.pventure.domain.tripFolder.service;

import com.example.pventure.domain.folder.dto.response.FolderResponseDto;
import com.example.pventure.domain.folder.entity.Folder;
import com.example.pventure.domain.folder.repository.FolderRepository;
import com.example.pventure.domain.member.dto.response.MemberSummaryDto;
import com.example.pventure.domain.member.service.MemberService;
import com.example.pventure.domain.trip.dto.response.TripResponseDto;
import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.trip.enums.TripStatus;
import com.example.pventure.domain.trip.repository.TripRepository;
import com.example.pventure.domain.tripFolder.entity.TripFolder;
import com.example.pventure.domain.tripFolder.repository.TripFolderRepository;
import com.example.pventure.domain.user.entity.User;
import com.example.pventure.domain.user.enums.SocialProvider;
import com.example.pventure.domain.user.repository.UserRepository;
import com.example.pventure.global.exception.ApiException;
import com.example.pventure.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TripFolderServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TripRepository tripRepository;

    @Mock
    private FolderRepository folderRepository;

    @Mock
    private TripFolderRepository tripFolderRepository;

    @Mock
    private MemberService memberService;

    @Spy
    @InjectMocks
    private TripFolderServiceImpl tripFolderService;

    private User user;
    private Folder folder;
    private Trip trip;
    private TripFolder tripFolder;
    private Folder defaultFolder;

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

        defaultFolder = Folder.builder()
                .name("기본 폴더")
                .isDefault(true)
                .user(user)
                .build();
        setId(defaultFolder, 1L);

        folder = Folder.builder()
                .name("사용자 지정 폴더")
                .isDefault(false)
                .user(user)
                .build();
        setId(folder, 2L);

        trip = Trip.builder()
                .title("제주 여행")
                .thumbnail("thumbnail")
                .destination("제주")
                .status(TripStatus.COMPLETED)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(3))
                .build();
        setId(trip, 1L);

        tripFolder = new TripFolder(trip, folder);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    }

    private void setId(Object entity, Long id) throws Exception {
        Field idField = entity.getClass().getSuperclass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(entity, id);
    }

    @DisplayName("addTrip 성공: 폴더에 여행 추가, memberService 호출 검증")
    @Test
    void addTrip_success() {
        Long userId = 1L;
        Long tripId = 1L;
        Long folderId = 2L;

        doReturn(Optional.of(trip)).when(tripRepository).findById(tripId);
        doReturn(Optional.of(folder)).when(folderRepository).findByIdAndUser(folderId, user);
        doReturn(tripFolder).when(tripFolderService).createTripFolder(trip, folder);

        FolderResponseDto response = tripFolderService.addTrip(folderId, tripId, userId);

        assertNotNull(response);
        assertEquals(folderId, response.getId());
        verify(memberService).isMember(any(User.class), any(Trip.class));
    }

    @DisplayName("addTrip 실패: 여행이 없으면 NOT_FOUND_TRIP 예외 발생")
    @Test
    void addTrip_fail_tripNotFound() {
        Long userId = 1L;
        Long tripId = 99L;

        when(tripRepository.findById(tripId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tripFolderService.addTrip(1L, tripId, userId))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining(ErrorCode.NOT_FOUND_TRIP.getMessage());

        verify(tripRepository).findById(tripId);
        verifyNoMoreInteractions(folderRepository);
    }

    @DisplayName("addTrip 실패: 폴더가 없으면 NOT_FOUND_FOLDER 예외 발생")
    @Test
    void addTrip_fail_folderNotFound() {
        Long userId = 1L;
        Long folderId = 99L;
        Long tripId = 1L;

        when(tripRepository.findById(tripId)).thenReturn(Optional.of(trip));
        when(folderRepository.findByIdAndUser(folderId, user)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tripFolderService.addTrip(folderId, tripId, userId))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining(ErrorCode.NOT_FOUND_FOLDER.getMessage());

        verify(folderRepository).findByIdAndUser(folderId, user);
    }

    @DisplayName("getTrips 성공: 폴더에 속한 여행 조회, TripResponseDto 반환")
    @Test
    void getTrips_success() {
        when(folderRepository.findByIdAndUser(2L, user)).thenReturn(Optional.of(folder));
        when(tripFolderRepository.findByFolder(folder)).thenReturn(List.of(tripFolder));
        when(memberService.getMemberSummaryDtoList(trip)).thenReturn(List.of(new MemberSummaryDto(1L,"홍길동", "image")));

        List<TripResponseDto> result = tripFolderService.getTrips(2L, 1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("제주 여행");

        verify(memberService).getMemberSummaryDtoList(trip);
        verify(tripFolderRepository).findByFolder(folder);
    }

    @DisplayName("getTrips 실패: 폴더가 없으면 NOT_FOUND_FOLDER 예외 발생")
    @Test
    void getTrips_fail_folderNotFound() {
        when(folderRepository.findByIdAndUser(2L, user)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tripFolderService.getTrips(2L, 1L))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining(ErrorCode.NOT_FOUND_FOLDER.getMessage());
    }

    @DisplayName("deleteTrip 성공: 일반 폴더, 여러 폴더에 속한 여행 삭제")
    @Test
    void deleteTrip_success_normalFolder_multipleTrips() {
        when(tripRepository.findById(10L)).thenReturn(Optional.of(trip));
        when(folderRepository.findByIdAndUser(2L, user)).thenReturn(Optional.of(folder));
        when(tripFolderRepository.existsMoreThanOneByTrip(trip)).thenReturn(true);

        tripFolderService.deleteTrip(2L, 10L, 1L);

        verify(tripFolderRepository).deleteByTripAndFolder(trip, folder);
        verify(tripFolderRepository, never()).save(any());
    }

    @DisplayName("deleteTrip 실패: 기본 폴더 마지막 여행 삭제 → TRIP_MUST_BELONG_TO_AT_LEAST_ONE_FOLDER 예외")
    @Test
    void deleteTrip_fail_defaultFolder_lastTrip() {
        when(tripRepository.findById(10L)).thenReturn(Optional.of(trip));
        when(folderRepository.findByIdAndUser(3L, user)).thenReturn(Optional.of(defaultFolder));
        when(tripFolderRepository.existsMoreThanOneByTrip(trip)).thenReturn(false);

        assertThatThrownBy(() -> tripFolderService.deleteTrip(3L, 10L, 1L))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining(ErrorCode.TRIP_MUST_BELONG_TO_AT_LEAST_ONE_FOLDER.getMessage());
    }

    @DisplayName("deleteTrip 성공: 일반 폴더 마지막 여행 삭제 → 기본 폴더로 이동")
    @Test
    void deleteTrip_success_moveToDefaultFolder() {
        when(tripRepository.findById(10L)).thenReturn(Optional.of(trip));
        when(folderRepository.findByIdAndUser(2L, user)).thenReturn(Optional.of(folder));
        when(tripFolderRepository.existsMoreThanOneByTrip(trip)).thenReturn(false);
        when(folderRepository.findDefaultFolderByUser(user)).thenReturn(Optional.of(defaultFolder));

        tripFolderService.deleteTrip(2L, 10L, 1L);

        verify(tripFolderRepository).save(any(TripFolder.class));
        verify(folderRepository).findDefaultFolderByUser(user);
    }

    @DisplayName("deleteTrip 실패: 여행이 없으면 NOT_FOUND_TRIP 예외 발생")
    @Test
    void deleteTrip_fail_tripNotFound() {
        when(tripRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tripFolderService.deleteTrip(2L, 999L, 1L))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining(ErrorCode.NOT_FOUND_TRIP.getMessage());
    }

    @DisplayName("deleteTrip 실패: 폴더가 없으면 NOT_FOUND_FOLDER 예외 발생")
    @Test
    void deleteTrip_fail_folderNotFound() {
        when(tripRepository.findById(10L)).thenReturn(Optional.of(trip));
        when(folderRepository.findByIdAndUser(2L, user)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tripFolderService.deleteTrip(2L, 10L, 1L))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining(ErrorCode.NOT_FOUND_FOLDER.getMessage());
    }
}
