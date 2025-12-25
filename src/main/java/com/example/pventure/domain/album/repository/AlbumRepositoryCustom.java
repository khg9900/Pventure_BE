package com.example.pventure.domain.album.repository;

import com.example.pventure.domain.album.dto.query.AlbumWithPhotoCountQDto;
import java.util.List;

public interface AlbumRepositoryCustom {

    List<AlbumWithPhotoCountQDto> findAllByTripWithPhotoCount(Long tripId);

    AlbumWithPhotoCountQDto findByIdAndTripWithPhotoCount(Long albumId, Long tripId);
}
