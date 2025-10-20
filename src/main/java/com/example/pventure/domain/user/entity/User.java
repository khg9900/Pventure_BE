package com.example.pventure.domain.user.entity;

import com.example.pventure.domain.user.enums.SocialProvider;
import com.example.pventure.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
            @UniqueConstraint(
                name = "uk_users_provider",
                columnNames = {"social_provider", "provider_id"}
            )
    }
)
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String name;

    @Column(unique = true, length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialProvider socialProvider;

    @Column(nullable = false)
    private String providerId;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;


}
