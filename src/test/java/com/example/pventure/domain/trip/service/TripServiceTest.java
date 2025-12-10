package com.example.pventure.domain.trip.service;

import com.example.pventure.domain.folder.entity.Folder;
import com.example.pventure.domain.folder.service.FolderService;
import com.example.pventure.domain.member.dto.response.MemberSummaryDto;
import com.example.pventure.domain.member.service.MemberService;
import com.example.pventure.domain.trip.dto.request.TripRequestDto;
import com.example.pventure.domain.trip.dto.request.TripSearchRequestDto;
import com.example.pventure.domain.trip.dto.response.TripResponseDto;
import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.trip.enums.TripDateFilter;
import com.example.pventure.domain.trip.enums.TripStatus;
import com.example.pventure.domain.trip.repository.TripRepository;
import com.example.pventure.domain.trip.util.TripFinder;
import com.example.pventure.domain.tripFolder.service.TripFolderService;
import com.example.pventure.domain.user.entity.User;
import com.example.pventure.domain.user.enums.SocialProvider;
import com.example.pventure.domain.user.repository.UserRepository;
import com.example.pventure.global.exception.ApiException;
import com.example.pventure.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TripServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private TripRepository tripRepository;
    @Mock private MemberService memberService;
    @Mock private FolderService folderService;
    @Mock private TripFolderService tripFolderService;
    @Mock private TripFinder tripFinder;

    @InjectMocks
    private TripServiceImpl tripService;

    private User user;
    private Folder folder;
    private Trip trip;
    private TripRequestDto requestDto;
    private TripSearchRequestDto searchRequest;

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

        folder = Folder.builder()
                .name("기본 폴더")
                .user(user)
                .build();
        setId(folder, 1L);

        requestDto = new TripRequestDto(
                "제주 여행",
                "thumbnail",
                "제주",
                4,
                TripStatus.PLANNED,
                LocalDate.now(),
                LocalDate.now().plusDays(3),
                1L
        );

        trip = requestDto.toEntity();

        searchRequest = new TripSearchRequestDto();
        searchRequest.setStartDate(LocalDate.now());
        searchRequest.setEndDate(LocalDate.now().plusDays(3));
        searchRequest.setTripDateFilter(TripDateFilter.SPECIFIED);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    }

    private void setId(Object entity, Long id) throws Exception {
        Field idField = entity.getClass().getSuperclass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(entity, id);
    }

    @Test
    void createTrip_Success() {
        // given
        when(tripRepository.save(any(Trip.class))).thenReturn(trip);
        when(folderService.getFolderEntity(user, folder.getId())).thenReturn(folder);
        when(memberService.countMember(trip)).thenReturn(1L);
        when(memberService.getMemberSummaryDtoList(trip)).thenReturn(Collections.emptyList());

        // when
        TripResponseDto response = tripService.createTrip(1L, requestDto, true);

        // then
        assertThat(response.getTitle()).isEqualTo("제주 여행");
        verify(tripRepository, times(1)).save(any(Trip.class));
        verify(memberService, times(1)).registerOwner(user, trip);
        verify(tripFolderService, times(1)).createTripFolder(trip, folder);
    }

    @Test
    void getTrips_Success() {
        // given
        when(tripFinder.findByUserAndPeriod(user, searchRequest.getStartDate(), searchRequest.getEndDate(),true, searchRequest.getTripDateFilter()))
                .thenReturn(List.of(trip));
        when(memberService.isMember(user, trip)).thenReturn(true);
        when(memberService.countMember(trip)).thenReturn(1L);

        MemberSummaryDto memberDto = new MemberSummaryDto(1L, "홍길동", "hong@example.com","image");
        when(memberService.getMemberSummaryDtoList(trip)).thenReturn(List.of(memberDto));

        // when
        List<TripResponseDto> result = tripService.getTrips(1L, searchRequest, true);

        // then
        assertThat(result).hasSize(1);
        TripResponseDto response = result.get(0);
        assertThat(response.getTitle()).isEqualTo("제주 여행");
        assertThat(response.getMembers()).hasSize(1);
        assertThat(response.getMemberCount()).isEqualTo(1L);

        verify(tripFinder, times(1))
                .findByUserAndPeriod(user, searchRequest.getStartDate(), searchRequest.getEndDate(), true, searchRequest.getTripDateFilter());
    }

    @Test
    void getTrip_Success() {
        // given
        when(tripFinder.findById(1L, false)).thenReturn(trip);
        when(memberService.isMember(user, trip)).thenReturn(true);
        // when
        TripResponseDto response = tripService.getTrip(1L, 1L, false);

        // then
        assertThat(response.getTitle()).isEqualTo("제주 여행");
        verify(memberService, times(1)).isMember(user, trip);
    }

    @Test
    void getTrip_Unauthorized_Throws() {
        // given
        when(tripFinder.findById(1L, true)).thenReturn(trip);
        when(memberService.isMember(user, trip)).thenReturn(false);

        // when & then
        ApiException ex = assertThrows(ApiException.class, () ->
                tripService.getTrip(1L, 1L, true)
        );

        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS);
    }

    @Test
    void updateTrip_Success() {
        // given
        when(tripFinder.findById(1L, true)).thenReturn(trip);
        when(memberService.canEdit(user, trip)).thenReturn(true);
        when(memberService.countMember(trip)).thenReturn(1L);
        when(memberService.getMemberSummaryDtoList(trip)).thenReturn(Collections.emptyList());

        TripRequestDto updateDto = new TripRequestDto(
                "서울 여행", "thumbnail2", "서울", 3, TripStatus.PLANNED,
                LocalDate.now(), LocalDate.now().plusDays(2), 1L
        );

        // when
        TripResponseDto response = tripService.updateTrip(1L, 1L, tripUpdateDto, true);

        // then
        assertThat(response.getTitle()).isEqualTo("서울 여행");
        verify(memberService, times(1)).canEdit(user, trip);
    }

    @Test
    void updateTrip_Unauthorized_Throws() {
        // given
        when(tripFinder.findById(1L, true)).thenReturn(trip);
        when(memberService.canEdit(user, trip)).thenReturn(false);

        // when & then
        ApiException ex = assertThrows(ApiException.class, () ->
                tripService.updateTrip(1L, 1L, tripUpdateDto, true)
        );

        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS);
        verify(memberService, times(1)).canEdit(user, trip);
        verify(tripRepository, never()).save(any());
    }

    @Test
    void deleteTrip_Success() {
        // given
        when(tripFinder.findById(1L, true)).thenReturn(trip);
        when(memberService.canDelete(user, trip)).thenReturn(true);

        // when
        tripService.deleteTrip(1L, 1L);

        // then
        verify(memberService, times(1)).canDelete(user, trip);
        verify(tripRepository, times(1)).delete(trip);
    }

    @Test
    void deleteTrip_Unauthorized_Throws() {
        // given
        when(tripFinder.findById(1L, true)).thenReturn(trip);
        when(memberService.canDelete(user, trip)).thenReturn(false);

        // when & then
        ApiException ex = assertThrows(ApiException.class, () ->
                tripService.deleteTrip(1L, 1L)
        );

        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.UNAUTHORIZED_MEMBER_ACCESS);
        verify(memberService, times(1)).canDelete(user, trip);
        verify(tripRepository, never()).delete(any());
    }
}
