package com.example.pventure.domain.photo.dto.response;

import com.example.pventure.domain.photo.entity.Photo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "사진 목록 조회 응답 DTO")
public class PhotoResponseDto {

    @Schema(description = "사진 ID", example = "1")
    private Long id;

    @Schema(
        description = "사진 조회용 Presigned URL",
        example = "https://bucket.s3.ap-northeast-2.amazonaws.com/photos/...?...X-Amz-Signature=..."
    )
    private String downloadUrl;

    public static PhotoResponseDto from (Photo photo, String downloadUrl) {
        return PhotoResponseDto.builder()
            .id(photo.getId())
            .downloadUrl(downloadUrl)
            .build();
    }
}