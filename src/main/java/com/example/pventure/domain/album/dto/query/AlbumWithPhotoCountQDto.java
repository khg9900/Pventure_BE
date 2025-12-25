package com.example.pventure.domain.album.dto.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlbumWithPhotoCountQDto {

    private final Long id;

    private final String title;

    private final Long photoCount;
}
