package com.example.pventure.domain.folder.dto.response;

import com.example.pventure.domain.folder.entity.Folder;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "폴더 정보 + 포함된 여행 수")
public class FolderCountResponseDto {

    @Schema(description = "폴더 ID", example = "1")
    private Long id;

    @Schema(description = "폴더 이름", example = "여행 폴더")
    private String name;

    @Schema(description = "해당 폴더에 포함된 여행 수", example = "5")
    private Long countTrip;

    public static FolderCountResponseDto from(Folder folder, Long countTrip) {
        return FolderCountResponseDto.builder()
                .id(folder.getId())
                .name(folder.getName())
                .countTrip(countTrip)
                .build();
    }
}
