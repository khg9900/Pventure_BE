package com.example.pventure.domain.photo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "사진 이동 요청 DTO")
public class MovePhotoRequestDto {

    @Schema(description = "이동할 사진 ID 목록", example = "[1, 2, 3]")
    @NotEmpty(message = "이동할 사진 ID 목록은 필수입니다.")
    private List<Long> photoIds;

    @Schema(description = "이동 대상 앨범 ID (null이면 앨범 미지정)", example = "1", nullable = true)
    private Long targetAlbumId;
}