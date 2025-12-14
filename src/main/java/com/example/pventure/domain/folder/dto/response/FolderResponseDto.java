package com.example.pventure.domain.folder.dto.response;

import com.example.pventure.domain.folder.entity.Folder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FolderResponseDto {
    private Long id;
    private String name;

    public static FolderResponseDto from(Folder folder) {
        return FolderResponseDto.builder()
                .id(folder.getId())
                .name(folder.getName())
                .build();
    }
}