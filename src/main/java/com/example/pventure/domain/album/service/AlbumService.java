package com.example.pventure.domain.album.service;

import com.example.pventure.domain.album.dto.request.AlbumRequestDto;
import com.example.pventure.domain.album.dto.response.AlbumResponseDto;
import java.util.List;

public interface AlbumService {

    AlbumResponseDto createAlbum(Long userId, Long tripId, AlbumRequestDto requestDto);

    List<AlbumResponseDto> getAlbums(Long userId, Long tripId);

    AlbumResponseDto getAlbum(Long userId, Long tripId, Long albumId);

    AlbumResponseDto updateAlbum(Long userId, Long tripId, Long albumId, AlbumRequestDto requestDto);

    void deleteAlbum(Long userId, Long tripId, Long albumId);

}
