package com.example.pventure.domain.photo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "사진 업로드용 Presigned URL 응답 DTO")
public class UploadUrlResponseDto {

    @Schema(
        description = "업로드할 객체의 S3 키",
        example = "trips/1/photos/uuid_IMG_1234.JPG"
    )
    private String s3Key;

    @Schema(
        description = "사진 업로드용 Presigned URL",
        example = "https://bucket.s3.ap-northeast-2.amazonaws.com/photos/...?...X-Amz-Signature=..."
    )
    private String uploadUrl;

    @Schema(description = "원본 파일명", example = "IMG_1234.JPG")
    private String originalFileName;
}
