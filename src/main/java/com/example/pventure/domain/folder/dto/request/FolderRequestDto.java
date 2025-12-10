package com.example.pventure.domain.folder.dto.request;

import com.example.pventure.domain.folder.entity.Folder;
import com.example.pventure.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "폴더 생성/수정 요청 DTO")
public class FolderRequestDto {

    @Schema(description = "폴더 이름", example = "여행 폴더")
    @NotBlank(message = "폴더 이름을 입력해주세요.")
    private String name;

    public Folder toEntity(User user) {
        return Folder.builder()
                .name(name)
                .user(user)
                .build();
    }
}
