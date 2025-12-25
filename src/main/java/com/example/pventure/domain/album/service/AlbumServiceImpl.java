package com.example.pventure.domain.album.service;

import com.example.pventure.domain.album.dto.query.AlbumWithPhotoCountQDto;
import com.example.pventure.domain.album.dto.request.AlbumRequestDto;
import com.example.pventure.domain.album.dto.response.AlbumResponseDto;
import com.example.pventure.domain.album.entity.Album;
import com.example.pventure.domain.album.repository.AlbumRepository;
import com.example.pventure.domain.photo.entity.Photo;
import com.example.pventure.domain.photo.repository.PhotoRepository;
import com.example.pventure.domain.photo.service.PhotoService;
import com.example.pventure.domain.trip.entity.Trip;
import com.example.pventure.domain.trip.service.TripPermissionService;
import com.example.pventure.global.exception.ApiException;
import com.example.pventure.global.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final TripPermissionService tripPermissionService;
    private final PhotoService photoService;
    private final PhotoRepository photoRepository;

    @Override
    public AlbumResponseDto createAlbum(Long userId, Long tripId, AlbumRequestDto requestDto) {

        Trip trip = tripPermissionService.getEditableTrip(userId, tripId);

        Album album = requestDto.toEntity(trip);

        Album savedAlbum = albumRepository.save(album);

        return AlbumResponseDto.from(savedAlbum, 0L);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlbumResponseDto> getAlbums(Long userId, Long tripId) {

        Trip trip = tripPermissionService.getViewableTrip(userId, tripId);

        List<AlbumWithPhotoCountQDto> albums =
            albumRepository.findAllByTripWithPhotoCount(trip.getId());

        return albums.stream()
            .map(AlbumResponseDto::from)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AlbumResponseDto getAlbum(Long userId, Long tripId, Long albumId) {

        Trip trip = tripPermissionService.getViewableTrip(userId, tripId);

        AlbumWithPhotoCountQDto album = albumRepository.findByIdAndTripWithPhotoCount(
            albumId, trip.getId());

        if (album == null) {
            throw new ApiException(ErrorCode.NOT_FOUND_ALBUM);
        }

        return AlbumResponseDto.from(album);
    }

    @Override
    public AlbumResponseDto updateAlbum(Long userId, Long tripId, Long albumId, AlbumRequestDto requestDto) {

        Trip trip = tripPermissionService.getEditableTrip(userId, tripId);

        Album album = albumRepository.findByIdAndTrip(albumId, trip)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_ALBUM));

        album.updateTitle(requestDto.getTitle());

        return AlbumResponseDto.from(album, photoService.countPhotos(album));
    }

    @Override
    public void deleteAlbum(Long userId, Long tripId, Long albumId) {

        Trip trip = tripPermissionService.getEditableTrip(userId, tripId);

        Album album = albumRepository.findByIdAndTrip(albumId, trip)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_ALBUM));

        List<Photo> photos = photoRepository.findAllByAlbum(album);

        photos.forEach(Photo::removeAlbum);

        albumRepository.delete(album);
    }
}
