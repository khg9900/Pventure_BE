package com.example.pventure.domain.photo.dto.response;

import com.example.pventure.domain.photo.entity.Photo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "사진 상세 조회 응답 DTO")
public class PhotoDetailResponseDto {

    @Schema(description = "사진 ID", example = "1")
    private Long id;

    @Schema(
        description = "사진 조회용 Presigned URL",
        example = "https://bucket.s3.ap-northeast-2.amazonaws.com/photos/...?...X-Amz-Signature=..."
    )
    private String downloadUrl;

    @Schema(description = "업로더 이름", example = "홍길동")
    private String uploaderName;

    @Schema(description = "원본 파일명", example = "IMG_1234.JPG")
    private String originalFileName;

    @Schema(description = "파일 유형", example = "image/jpeg")
    private String contentType;

    @Schema(description = "파일 크기(Byte)", example = "345678")
    private Long fileSize;

    @Schema(description = "업로드 시각", example = "2025-12-25T10:00:00")
    private LocalDateTime createdAt;

    public static PhotoDetailResponseDto from (Photo photo, String downloadUrl) {
        return PhotoDetailResponseDto.builder()
            .id(photo.getId())
            .downloadUrl(downloadUrl)
            .uploaderName(photo.getUploader().getName())
            .originalFileName(photo.getOriginalFileName())
            .contentType(photo.getContentType())
            .fileSize(photo.getFileSize())
            .createdAt(photo.getCreatedAt())
            .build();
    }
}
