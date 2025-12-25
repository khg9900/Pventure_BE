package com.example.pventure.domain.photo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "S3 업로드용 Presigned URL 생성 요청 DTO")
public class UploadUrlRequestDto {

    @Schema(description = "원본 파일명", example = "IMG_1234.JPG")
    @NotBlank(message = "파일명을 입력해주세요.")
    private String originalFileName;

    @Schema(description = "파일 유형", example = "image/jpeg")
    @NotBlank(message = "파일 유형을 입력해주세요.")
    @Pattern(regexp = "^image/(jpeg|jpg|png|webp)$", message = "지원되는 이미지 형식이 아닙니다.")
    private String contentType;

    @Schema(description = "파일 크기(Byte)", example = "345678")
    @NotNull(message = "파일 크기를 입력해주세요.")
    @Min(value = 1, message = "파일 크기는 1바이트 이상이어야 합니다.")
    private Long fileSize;

}