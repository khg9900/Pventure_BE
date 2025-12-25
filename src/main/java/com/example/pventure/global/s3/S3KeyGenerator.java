package com.example.pventure.global.s3;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class S3KeyGenerator {

    public String generatePhotoKey(Long tripId, String originalFilename) {
        String ext = extractExtension(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return String.format("trips/%d/photos/%s.%s", tripId, uuid, ext);
    }

    public String generateTripThumbnailKey(Long tripId, String originalFilename) {
        String ext = extractExtension(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return String.format("trips/%d/thumbnail/%s.%s", tripId, uuid, ext);
    }

    public String generateProfileImgKey(Long userId, String originalFilename) {
        String ext = extractExtension(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return String.format("users/%d/profiles/%s.%s", userId, uuid, ext);
    }

    private String extractExtension(String originalFilename) {
        int index = originalFilename.lastIndexOf(".");
        return originalFilename.substring(index + 1);
    }
}
