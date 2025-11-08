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
public class FolderCountResponseDto {
    private Long id;
    private String name;
    private Long CountFolder;

    public static FolderCountResponseDto from(Folder folder, Long countFolder) {
        return FolderCountResponseDto.builder()
                .id(folder.getId())
                .name(folder.getName())
                .CountFolder(countFolder)
                .build();
    }

}
