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
    private Long countTrip;

    public static FolderCountResponseDto from(Folder folder, Long countTrip) {

        return FolderCountResponseDto.builder()
                .id(folder.getId())
                .name(folder.getName())
                .countTrip(countTrip)
                .build();
    }

}
