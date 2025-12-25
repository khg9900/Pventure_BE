package com.example.pventure.domain.album.dto.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class AlbumWithPhotoCountQDto {

    private final Long id;

    private final String title;

    private final Long photoCount;
}
