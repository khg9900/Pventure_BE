package com.example.pventure.domain.album.dto.request;

import com.example.pventure.domain.album.entity.Album;
import com.example.pventure.domain.trip.entity.Trip;
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
@Schema(description = "앨범 생성/수정 요청 DTO")
public class AlbumRequestDto {

    @Schema(description = "앨범 이름", example = "1일차 앨범")
    @NotBlank(message = "앨범 이름을 입력해주세요.")
    private String title;

    public Album toEntity(Trip trip) {
        return Album.builder()
            .trip(trip)
            .title(title)
            .build();
    }
}
