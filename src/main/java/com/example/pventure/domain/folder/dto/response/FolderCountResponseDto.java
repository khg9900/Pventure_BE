package com.example.pventure.domain.folder.dto.response;

import com.example.pventure.domain.folder.entity.Folder;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

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
