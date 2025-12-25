package com.example.pventure.domain.album.dto.response;

import com.example.pventure.domain.album.dto.query.AlbumWithPhotoCountQDto;
import com.example.pventure.domain.album.entity.Album;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "앨범 응답 DTO")
public class AlbumResponseDto {

     @Schema(description = "앨범 ID", example = "1")
     private Long id;

     @Schema(description = "앨범 제목", example = "1일차 앨범")
     private String title;

     @Schema(description = "사진 개수", example = "10")
     private Long photoCount;

     public static AlbumResponseDto from(Album album, Long photoCount) {
         return AlbumResponseDto.builder()
             .id(album.getId())
             .title(album.getTitle())
             .photoCount(photoCount)
             .build();
     }

     public static AlbumResponseDto from(AlbumWithPhotoCountQDto dto) {
          return AlbumResponseDto.builder()
              .id(dto.getId())
              .title(dto.getTitle())
              .photoCount(dto.getPhotoCount())
              .build();
     }
}
