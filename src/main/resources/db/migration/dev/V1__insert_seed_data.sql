-- 1. Users
INSERT INTO users (id, name, email, social_provider, provider_id, image_url, created_at, updated_at)
VALUES
    (1, '테스트 사용자1', 'test1@example.com', 'GOOGLE', 'google_001', 'https://example.com/image1.png', NOW(), NOW()),
    (2, '테스트 사용자2', 'test2@example.com', 'NAVER', 'naver_001', 'https://example.com/image2.png', NOW(), NOW()),
    (3, '테스트 사용자3', 'test3@example.com', 'KAKAO', 'kakao_001', NULL, NOW(), NOW());

-- 2. Team (user_id FK)
INSERT INTO team (id, user_id, created_at, updated_at)
VALUES
    (1, 1, NOW(), NOW()),
    (2, 2, NOW(), NOW());

-- 3. Folder (team_id FK)
INSERT INTO folder (id, name, team_id, is_default, created_at, updated_at)
VALUES
    (1, '기본 폴더', 1, TRUE, NOW(), NOW()),
    (2, '서울 여행', 2, FALSE, NOW(), NOW());

-- 4. Trip (team_id FK)
INSERT INTO trip (id, title, description, destination, start_date, end_date, status, team_id, created_at, updated_at)
VALUES
    (1, '서울 여행', '서울에서 3일간 여행', '서울', '2025-11-01', '2025-11-03', 'PLANNED', 1, NOW(), NOW()),
    (2, '부산 여행', '부산 해운대 여행', '부산', '2025-12-01', '2025-12-02', 'PLANNED', 2, NOW(), NOW());

-- 5. TripFolder (trip_id, folder_id FK)
INSERT INTO trip_folder (id, trip_id, folder_id, created_at, updated_at)
VALUES
    (1, 1, 2, NOW(), NOW()),
    (2, 2, 1, NOW(), NOW());

-- 6. Schedule (trip_id FK)
INSERT INTO schedule (id, trip_id, date, day, sequence, memo, created_at, updated_at)
VALUES
    (1, 1, '2025-11-01', 1, 1, '첫째날 일정', NOW(), NOW()),
    (2, 1, '2025-11-02', 2, 1, '둘째날 일정', NOW(), NOW());

-- 7. Place (schedule_id FK)
INSERT INTO place (id, name, link, address, latitude, longitude, place_category, schedule_id, created_at, updated_at)
VALUES
    (1, '경복궁', 'https://gyeongbokgung.or.kr', '서울 종로구 사직로 161', 37.579617, 126.977041, 'TOURIST_ATTRACTION', 1, NOW(), NOW()),
    (2, '남산타워', 'https://nseoultower.co.kr', '서울 용산구 남산공원길 105', 37.551169, 126.988227, 'TOURIST_ATTRACTION', 2, NOW(), NOW());

-- 8. Album (trip_id FK)
INSERT INTO album (id, trip_id, title, created_at, updated_at)
VALUES
    (1, 1, '서울 여행 앨범', NOW(), NOW());

-- 9. Photo (album_id FK)
INSERT INTO photo (id, album_id, url, caption, created_at, updated_at)
VALUES
    (1, 1, 'https://example.com/photo1.png', '경복궁 입구', NOW(), NOW()),
    (2, 1, 'https://example.com/photo2.png', '남산타워 전망', NOW(), NOW());

-- 10. Member (user_id, team_id FK)
INSERT INTO member (id, user_id, team_id, team_role, team_status, created_at, updated_at)
VALUES
    (1, 1, 1, 'OWNER', 'ACCEPTED', NOW(), NOW()),
    (2, 2, 1, 'EDITOR', 'ACCEPTED', NOW(), NOW()),
    (3, 3, 1, 'VIEWER', 'ACCEPTED', NOW(), NOW());

