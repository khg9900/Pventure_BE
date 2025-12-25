package com.example.pventure.domain.album.repository;

import com.example.pventure.domain.album.dto.query.AlbumWithPhotoCountQDto;
import com.example.pventure.domain.album.entity.QAlbum;
import com.example.pventure.domain.photo.entity.QPhoto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AlbumRepositoryCustomImpl implements AlbumRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<AlbumWithPhotoCountQDto> findAllByTripWithPhotoCount(Long tripId) {

        QAlbum album = QAlbum.album;

        return queryAlbumWithPhotoCount()
            .where(album.trip.id.eq(tripId))
            .fetch();
    }

    @Override
    public AlbumWithPhotoCountQDto findByIdAndTripWithPhotoCount(Long albumId, Long tripId) {

        QAlbum album = QAlbum.album;

        return queryAlbumWithPhotoCount()
            .where(
                album.id.eq(albumId),
                album.trip.id.eq(tripId)
            )
            .fetchOne();
    }

    private JPAQuery<AlbumWithPhotoCountQDto> queryAlbumWithPhotoCount() {

        QAlbum album = QAlbum.album;
        QPhoto photo = QPhoto.photo;

        return queryFactory
            .select(Projections.constructor(
                AlbumWithPhotoCountQDto.class,
                album.id,
                album.title,
                photo.count()
            ))
            .from(album)
            .leftJoin(album.photos, photo)
            .groupBy(album.id, album.title);
    }
}
