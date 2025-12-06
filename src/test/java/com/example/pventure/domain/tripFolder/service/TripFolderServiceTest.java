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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TripFolderServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private TripRepository tripRepository;
    @Mock private FolderRepository folderRepository;
    @Mock private TripFolderRepository tripFolderRepository;
    @Mock private MemberService memberService;

    @Spy
    @InjectMocks
    private TripFolderServiceImpl tripFolderService;

    private User user;
    private Folder folder;
    private Folder defaultFolder;
    private Trip trip;
    private TripFolder tripFolder;

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
                .user(user)
                .build();
        setId(defaultFolder, 1L);

        folder = Folder.builder()
                .name("사용자 지정 폴더")
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

    @DisplayName("addTrip 성공: 폴더에 여행 추가 시 memberService 호출 확인")
    @Test
    void addTrip_success() {
        when(tripRepository.findById(anyLong())).thenReturn(Optional.of(trip));
        when(folderRepository.findByIdAndUser(anyLong(), any())).thenReturn(Optional.of(folder));
        when(memberService.isMember(any(), any())).thenReturn(true);
        doReturn(tripFolder).when(tripFolderService).createTripFolder(any(), any());

        FolderResponseDto response = tripFolderService.addTrip(2L, 1L, 1L);

        assertNotNull(response);
        assertEquals(folder.getId(), response.getId());
        verify(memberService).isMember(any(User.class), any(Trip.class));
        verify(tripFolderService).createTripFolder(any(Trip.class), any(Folder.class));
    }

    @DisplayName("addTrip 실패: 여행이 없으면 NOT_FOUND_TRIP 예외 발생")
    @Test
    void addTrip_fail_tripNotFound() {
        when(tripRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tripFolderService.addTrip(1L, 99L, 1L))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining(ErrorCode.NOT_FOUND_TRIP.getMessage());
    }

    @DisplayName("addTrip 실패: 폴더가 없으면 NOT_FOUND_FOLDER 예외 발생")
    @Test
    void addTrip_fail_folderNotFound() {
        when(tripRepository.findById(anyLong())).thenReturn(Optional.of(trip));
        when(folderRepository.findByIdAndUser(anyLong(), any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tripFolderService.addTrip(99L, 1L, 1L))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining(ErrorCode.NOT_FOUND_FOLDER.getMessage());
    }

    @DisplayName("getTrips 성공: 폴더에 속한 여행 목록 조회")
    @Test
    void getTrips_success() {
        when(folderRepository.findByIdAndUser(anyLong(), any())).thenReturn(Optional.of(folder));
        when(tripFolderRepository.findByFolder(any())).thenReturn(List.of(tripFolder));
        when(memberService.isMember(any(), any())).thenReturn(true);
        when(memberService.getMemberSummaryDtoList(any())).thenReturn(List.of(new MemberSummaryDto(1L, "홍길동", "example@gmail.com","image")));
        when(memberService.countMember(any())).thenReturn(1L);

        List<TripResponseDto> result = tripFolderService.getTrips(2L, 1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("제주 여행");

        verify(tripFolderRepository).findByFolder(any());
        verify(memberService).getMemberSummaryDtoList(any());
    }

    @DisplayName("getTrips 실패: 폴더가 없으면 NOT_FOUND_FOLDER 예외 발생")
    @Test
    void getTrips_fail_folderNotFound() {
        when(folderRepository.findByIdAndUser(anyLong(), any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tripFolderService.getTrips(2L, 1L))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining(ErrorCode.NOT_FOUND_FOLDER.getMessage());
    }

    @DisplayName("deleteTrip 성공: 일반 폴더에서 Trip 삭제 가능")
    @Test
    void deleteTrip_success_normalFolder_multipleFolders() {
        TripFolder tf1 = new TripFolder(trip, folder);
        TripFolder tf2 = new TripFolder(trip, defaultFolder);
        trip.getFolders().addAll(List.of(tf1, tf2));

        when(folderRepository.findByIdAndUser(anyLong(), any())).thenReturn(Optional.of(folder));
        when(tripRepository.findWithFolders(anyLong())).thenReturn(Optional.of(trip));
        when(tripFolderRepository.findByTripAndFolder(any(), any())).thenReturn(Optional.of(tf1));
        when(memberService.isMember(any(), any())).thenReturn(true);

        tripFolderService.deleteTrip(2L, 1L, 1L);

        verify(tripFolderRepository).delete(tf1);
    }

    @DisplayName("deleteTrip 실패: Trip 없음 → NOT_FOUND_TRIP 예외 발생")
    @Test
    void deleteTrip_fail_tripNotFound() {
        when(folderRepository.findByIdAndUser(anyLong(), any())).thenReturn(Optional.of(folder));
        when(tripRepository.findWithFolders(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tripFolderService.deleteTrip(2L, 999L, 1L))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining(ErrorCode.NOT_FOUND_TRIP.getMessage());
    }

    @DisplayName("deleteTrip 실패: 폴더 없음 → NOT_FOUND_FOLDER 예외 발생")
    @Test
    void deleteTrip_fail_folderNotFound() {
        when(folderRepository.findByIdAndUser(anyLong(), any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tripFolderService.deleteTrip(999L, 1L, 1L))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining(ErrorCode.NOT_FOUND_FOLDER.getMessage());
    }
}
