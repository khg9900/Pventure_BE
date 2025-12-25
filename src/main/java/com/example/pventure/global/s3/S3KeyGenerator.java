package com.example.pventure.global.s3;

import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class S3KeyGenerator {

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "webp");

    public String generatePhotoKey(Long tripId, String originalFilename) {
        String ext = extractAndValidateExtension(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return String.format("trips/%d/photos/%s.%s", tripId, uuid, ext);
    }

    public String generateTripThumbnailKey(Long tripId, String originalFilename) {
        String ext = extractAndValidateExtension(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return String.format("trips/%d/thumbnail/%s.%s", tripId, uuid, ext);
    }

    public String generateProfileImgKey(Long userId, String originalFilename) {
        String ext = extractAndValidateExtension(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return String.format("users/%d/profiles/%s.%s", userId, uuid, ext);
    }

    private String extractAndValidateExtension(String originalFilename) {
        if (originalFilename == null || originalFilename.isBlank()) {
            throw new IllegalArgumentException("파일명은 비어 있을 수 없습니다.");
        }

        int index = originalFilename.lastIndexOf(".");
        if (index == -1 || index == originalFilename.length() - 1) {
            throw new IllegalArgumentException("파일 확장자가 존재하지 않습니다.");
        }

        String ext = originalFilename.substring(index + 1).toLowerCase();

        if (!ALLOWED_EXTENSIONS.contains(ext)) {
            throw new IllegalArgumentException("허용되지 않은 파일 확장자입니다: " + ext);
        }

        return ext;
    }
}
