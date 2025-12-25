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

    private static final QAlbum album = QAlbum.album;
    private static final QPhoto photo = QPhoto.photo;

    @Override
    public List<AlbumWithPhotoCountQDto> findAllByTripWithPhotoCount(Long tripId) {
        return queryAlbumWithPhotoCount()
            .where(album.trip.id.eq(tripId))
            .fetch();
    }

    @Override
    public AlbumWithPhotoCountQDto findByIdAndTripWithPhotoCount(Long albumId, Long tripId) {
        return queryAlbumWithPhotoCount()
            .where(
                album.id.eq(albumId),
                album.trip.id.eq(tripId)
            )
            .fetchOne();
    }

    private JPAQuery<AlbumWithPhotoCountQDto> queryAlbumWithPhotoCount() {
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
