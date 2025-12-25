package com.example.pventure.domain.photo.dto.request;

import com.example.pventure.domain.album.entity.Album;
import com.example.pventure.domain.photo.entity.Photo;
import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "S3 업로드 완료 후 사진 생성 요청 DTO")
public class PhotoRequestDto {

    @Schema(description = "S3 Key", example = "trips/1/photos/uuid_IMG_1234.JPG")
    @NotBlank(message = "S3 키 값을 입력해주세요.")
    private String s3Key;

    @Schema(description = "원본 파일명", example = "IMG_1234.JPG")
    @NotBlank(message = "파일명을 입력해주세요.")
    private String originalFileName;

    @Schema(description = "파일 유형", example = "image/jpeg")
    @NotBlank(message = "파일 유형을 입력해주세요.")
    private String contentType;

    @Schema(description = "파일 크기", example = "345678")
    @NotNull(message = "파일 크기를 입력해주세요.")
    @Min(value = 1, message = "파일 크기는 1바이트 이상이어야 합니다.")
    private Long fileSize;

    public Photo toEntity(Trip trip, Album album, User user) {
        return Photo.builder()
            .trip(trip)
            .album(album)
            .uploader(user)
            .s3Key(s3Key)
            .originalFileName(originalFileName)
            .contentType(contentType)
            .fileSize(fileSize)
            .build();
    }
}
