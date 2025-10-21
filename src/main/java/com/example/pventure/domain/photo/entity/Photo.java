package com.example.pventure.domain.photo.entity;

import com.example.pventure.domain.album.entity.Album;
import com.example.pventure.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Photo extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;
  
    @Column(nullable = false,columnDefinition = "TEXT")
    private String url;

    @Column(length = 100)
    private String caption;
}
