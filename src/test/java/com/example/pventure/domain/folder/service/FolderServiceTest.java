package com.example.pventure.domain.folder.service;

import com.example.pventure.domain.folder.dto.request.FolderRequestDto;
import com.example.pventure.domain.folder.dto.response.FolderCountResponseDto;
import com.example.pventure.domain.folder.dto.response.FolderResponseDto;
import com.example.pventure.domain.folder.entity.Folder;
import com.example.pventure.domain.folder.repository.FolderRepository;
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
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FolderServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private FolderRepository folderRepository;

    @InjectMocks
    private FolderServiceImpl folderService;

    private User user;
    private Folder defaultFolder;
    private Folder normalFolder;
    private FolderRequestDto defaultFolderRequestDto;
    private FolderRequestDto normalFolderRequestDto;

    @BeforeEach
    void setUp() throws Exception {
        // 공통 User
        user = User.builder()
                .name("홍길동")
                .email("hong@example.com")
                .socialProvider(SocialProvider.GOOGLE)
                .providerId("google-id-123")
                .imageUrl("https://example.com/image.jpg")
                .build();
        setId(user, 1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        defaultFolder = Folder.builder()
                .name("기본 폴더")
                .isDefault(true)
                .user(user)
                .build();
        setId(defaultFolder, 1L);

        normalFolder = Folder.builder()
                .name("테스트 폴더")
                .isDefault(false)
                .user(user)
                .build();
        setId(normalFolder, 2L);

        // FolderRequestDto 통일
        defaultFolderRequestDto = new FolderRequestDto("기본 폴더", true);
        normalFolderRequestDto = new FolderRequestDto("테스트 폴더", false);
    }

    private void setId(Object entity, Long id) throws Exception {
        Field idField = entity.getClass().getSuperclass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(entity, id);
    }

    @Test
    @DisplayName("createFolder: 새로운 폴더 생성 성공")
    void createFolder_success() {
        when(folderRepository.save(any(Folder.class))).thenReturn(normalFolder);

        FolderResponseDto response = folderService.createFolder(normalFolderRequestDto, 1L);

        assertEquals("테스트 폴더", response.getName());
        verify(folderRepository).save(any(Folder.class));
    }

    @Test
    @DisplayName("createFolder: 기본 폴더 중복 생성 시 실패")
    void createFolder_fail_duplicateDefault() {
        when(folderRepository.hasDefault(user)).thenReturn(true);

        assertThatThrownBy(() -> folderService.createFolder(defaultFolderRequestDto, 1L))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining(ErrorCode.DUPLICATE_FOLDER.getMessage());
    }

    @Test
    @DisplayName("getAllFolders: 유저의 모든 폴더 조회 성공")
    void getAllFolders_success() {
        when(folderRepository.findAllByUser(user)).thenReturn(List.of(defaultFolder, normalFolder));

        List<FolderCountResponseDto> result = folderService.getAllFolders(1L);

        assertThat(result).hasSize(2);
        assertEquals("기본 폴더", result.get(0).getName());
        assertEquals("테스트 폴더", result.get(1).getName());
    }

    @Test
    @DisplayName("getFolder: 폴더 조회 성공")
    void getFolder_success() {
        when(folderRepository.findByIdAndUser(2L, user)).thenReturn(Optional.of(normalFolder));

        FolderResponseDto result = folderService.getFolder(2L, 1L);

        assertEquals("테스트 폴더", result.getName());
    }

    @Test
    @DisplayName("getFolder: 폴더가 존재하지 않아 실패")
    void getFolder_fail_notFound() {
        when(folderRepository.findByIdAndUser(99L, user)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> folderService.getFolder(99L, 1L))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining(ErrorCode.NOT_FOUND_FOLDER.getMessage());
    }

    @Test
    @DisplayName("updateFolder: 일반 폴더 이름 변경 성공")
    void updateFolder_success() {
        when(folderRepository.findByIdAndUser(2L, user)).thenReturn(Optional.of(normalFolder));

        FolderResponseDto result = folderService.updateFolder(2L, normalFolderRequestDto, 1L);

        assertEquals("테스트 폴더", result.getName());
    }

    @Test
    @DisplayName("updateFolder: 기본 폴더 수정 시 실패")
    void updateFolder_fail_defaultFolder() {
        when(folderRepository.findByIdAndUser(1L, user)).thenReturn(Optional.of(defaultFolder));

        assertThatThrownBy(() -> folderService.updateFolder(1L, defaultFolderRequestDto, 1L))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining(ErrorCode.CANNOT_EDIT_DEFAULT_FOLDER.getMessage());
    }

    @Test
    @DisplayName("deleteFolder: 일반 폴더 삭제 시 트립 이동 후 삭제 성공")
    void deleteFolder_success_moveTripsToDefault() {
        when(folderRepository.findWithTrips(2L, user)).thenReturn(Optional.of(normalFolder));
        when(folderRepository.findDefaultByUser(user)).thenReturn(Optional.of(defaultFolder));

        folderService.deleteFolder(2L, 1L);

        verify(folderRepository).delete(normalFolder);
    }

    @Test
    @DisplayName("deleteFolder: 기본 폴더 삭제 시 실패")
    void deleteFolder_fail_defaultFolder() {
        when(folderRepository.findWithTrips(1L, user)).thenReturn(Optional.of(defaultFolder));

        assertThatThrownBy(() -> folderService.deleteFolder(1L, 1L))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining(ErrorCode.CANNOT_DELETE_DEFAULT_FOLDER.getMessage());
    }
}
