package com.example.pventure.domain.album.controller;

import com.example.pventure.domain.album.docs.CreateAlbumDocs;
import com.example.pventure.domain.album.docs.DeleteAlbumDocs;
import com.example.pventure.domain.album.docs.GetAlbumDocs;
import com.example.pventure.domain.album.docs.GetAlbumsDocs;
import com.example.pventure.domain.album.docs.UpdateAlbumDocs;
import com.example.pventure.domain.album.dto.request.AlbumRequestDto;
import com.example.pventure.domain.album.dto.response.AlbumResponseDto;
import com.example.pventure.domain.album.service.AlbumService;
import com.example.pventure.global.response.CustomResponse;
import com.example.pventure.global.response.CustomResponseHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Album", description = "앨범 관련 API")
@RestController
@RequestMapping("/trips/{tripId}/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @CreateAlbumDocs
    @PostMapping
    public ResponseEntity<CustomResponse<AlbumResponseDto>> createAlbum(
        @RequestParam Long userId,
        @PathVariable Long tripId,
        @Valid @RequestBody AlbumRequestDto requestDto
    ) {
        return CustomResponseHelper.created(albumService.createAlbum(userId, tripId, requestDto));
    }

    @GetAlbumsDocs
    @GetMapping
    public ResponseEntity<CustomResponse<List<AlbumResponseDto>>> getAlbums(
        @RequestParam Long userId,
        @PathVariable Long tripId
    ){
        return CustomResponseHelper.ok(albumService.getAlbums(userId, tripId));
    }

    @GetAlbumDocs
    @GetMapping("/{albumId}")
    public ResponseEntity<CustomResponse<AlbumResponseDto>> getAlbum(
        @RequestParam Long userId,
        @PathVariable Long tripId,
        @PathVariable Long albumId
    ){
        return CustomResponseHelper.ok(albumService.getAlbum(userId, tripId, albumId));
    }

    @UpdateAlbumDocs
    @PutMapping("/{albumId}")
    public ResponseEntity<CustomResponse<AlbumResponseDto>> updateAlbum(
        @RequestParam Long userId,
        @PathVariable Long tripId,
        @PathVariable Long albumId,
        @Valid @RequestBody AlbumRequestDto requestDto
    ) {
        return CustomResponseHelper.ok(albumService.updateAlbum(userId, tripId, albumId, requestDto));
    }

    @DeleteAlbumDocs
    @DeleteMapping("/{albumId}")
    public ResponseEntity<Void> deleteAlbum(
        @RequestParam Long userId,
        @PathVariable Long tripId,
        @PathVariable Long albumId
    ) {
        albumService.deleteAlbum(userId, tripId, albumId);
        return CustomResponseHelper.noContent();
    }
}
