package com.example.pventure.domain.folder.service;

import com.example.pventure.domain.folder.dto.request.FolderRequestDto;
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
import java.util.Optional;

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
                .user(user)
                .build();
        setId(defaultFolder, 1L);

        normalFolder = Folder.builder()
                .name("테스트 폴더")
                .user(user)
                .build();
        setId(normalFolder, 2L);

        // FolderRequestDto 통일
        defaultFolderRequestDto = new FolderRequestDto("기본 폴더");
        normalFolderRequestDto = new FolderRequestDto("테스트 폴더");
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
}
