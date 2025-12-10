package com.example.pventure.domain.folder.dto.response;

import com.example.pventure.domain.folder.entity.Folder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "폴더 상세 응답 DTO")
public class FolderResponseDto {

    @Schema(description = "폴더 ID", example = "1")
    private Long id;

    @Schema(description = "폴더 이름", example = "여행 폴더")
    private String name;

    public static FolderResponseDto from(Folder folder) {
        return FolderResponseDto.builder()
                .id(folder.getId())
                .name(folder.getName())
                .build();
    }
}
