package com.example.pventure.domain.folder.dto.request;

import com.example.pventure.domain.folder.entity.Folder;
import com.example.pventure.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FolderRequestDto {

    @NotBlank(message = "폴더 이름을 입력해주세요.")
    private String name;

    public Folder toEntity(User user) {
        return Folder.builder()
                .name(name)
                .isDefault(false)
                .user(user)
                .build();
    }
}
