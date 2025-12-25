-- 1. Users
-- =====================================================
INSERT INTO users (id, name, email, social_provider, provider_id, image_url, created_at, updated_at)
VALUES (1, '테스트 사용자1', 'test1@example.com', 'GOOGLE', 'google_001', 'https://example.com/image1.png', NOW(), NOW()),
       (2, '테스트 사용자2', 'test2@example.com', 'NAVER', 'naver_001', 'https://example.com/image2.png', NOW(), NOW()),
       (3, '테스트 사용자3', 'test3@example.com', 'KAKAO', 'kakao_001', NULL, NOW(), NOW()),
       (4, '테스트 사용자4', 'test4@example.com', 'KAKAO', 'kakao_002', NULL, NOW(), NOW()),
       (5, '테스트 사용자5', 'test5@example.com', 'KAKAO', 'kakao_003', NULL, NOW(), NOW()),
       (6, '테스트 사용자6', 'test6@example.com', 'KAKAO', 'kakao_004', NULL, NOW(), NOW()),
       (7, '테스트 사용자7', 'test7@example.com', 'GOOGLE', 'google_007', 'https://example.com/image7.png', NOW(), NOW()),
       (8, '테스트 사용자8', 'test8@example.com', 'NAVER', 'naver_008', 'https://example.com/image8.png', NOW(), NOW()),
       (9, '테스트 사용자9', 'test9@example.com', 'KAKAO', 'kakao_009', NULL, NOW(), NOW()),
       (10, '테스트 사용자10', 'test10@example.com', 'KAKAO', 'kakao_010', NULL, NOW(), NOW());

-- =====================================================
-- 2. Folder (유저별 기본폴더)
-- =====================================================
INSERT INTO folder (id, name, user_id, created_at, updated_at)
VALUES
    (1, '기본 폴더', 1, NOW(), NOW()),
    (2, '기본 폴더', 2, NOW(), NOW()),
    (3, '기본 폴더', 3, NOW(), NOW()),
    (4, '기본 폴더', 4, NOW(), NOW()),
    (5, '기본 폴더', 5, NOW(), NOW()),
    (6, '기본 폴더', 6, NOW(), NOW()),
    (7, '기본 폴더', 7, NOW(), NOW()),
    (8, '기본 폴더', 8, NOW(), NOW()),
    (9, '기본 폴더', 9, NOW(), NOW()),
    (10, '기본 폴더', 10, NOW(), NOW());

-- =====================================================
-- 3. Trip (소유자: 기본폴더와 연결)
-- =====================================================
INSERT INTO trip (id, title, thumbnail, destination, start_date, end_date, total_duration, status, created_at, updated_at)
VALUES
    (1, '서울 여행', 'https://gyeongbokgung.or.kr', '서울', '2025-11-01', '2025-11-03', 3 ,'PLANNED', NOW(), NOW()), -- owner: user2
    (2, '부산 여행', 'https://nseoultower.co.kr', '부산', '2025-12-01', '2025-12-02', 2,'PLANNED', NOW(), NOW()), -- owner: user1
    (3, '제주 여행', 'https://jeju.go.kr', '제주', '2025-11-10', '2025-11-15', 6, 'PLANNED', NOW(), NOW()), -- owner: user7
    (4, '강릉 여행', 'https://gangneung.go.kr', '강릉', '2025-12-05', '2025-12-07', 3, 'PLANNED', NOW(), NOW()), -- owner: user10
    (5, '여수 여행', 'https://yeosu.go.kr', '여수', '2025-12-15', '2025-12-18', 4, 'PLANNED', NOW(), NOW()),  -- owner: user2
    (6, '미할당 여행', 'https://example.com', '미정', '2025-12-20', '2025-12-22', 3, 'PLANNED', NOW(), NOW()); -- unassigned

-- =====================================================
-- 4. TripFolder (Trip → 소유자 기본폴더)
-- =====================================================
INSERT INTO trip_folder (id, trip_id, folder_id, created_at, updated_at)
VALUES
    (1, 1, 2, NOW(), NOW()),  -- 서울 여행 → user2 기본 폴더
    (2, 2, 1, NOW(), NOW()),  -- 부산 여행 → user1 기본 폴더
    (3, 3, 7, NOW(), NOW()),  -- 제주 여행 → user7 기본 폴더
    (4, 4, 10, NOW(), NOW()), -- 강릉 여행 → user10 기본 폴더
    (5, 5, 2, NOW(), NOW());  -- 여수 여행 → user2 기본 폴더
-- Trip ID 6은 trip_folder에 매핑되지 않음 (미지정 상태)
-- =====================================================
-- 5. Schedule
-- =====================================================
INSERT INTO schedule (id, trip_id, day, sequence, memo, time_slot, is_completed, created_at, updated_at)
VALUES
    -- 서울 여행
    (1, 1, 1, 1, '경복궁 방문','MORNING', FALSE, NOW(), NOW()),
    (2, 1, 2, 1, '남산타워 전망대','MORNING', FALSE, NOW(), NOW()),
    -- 부산 여행
    (3, 2, 1, 1, '해운대 산책', 'MORNING', FALSE, NOW(), NOW()),
    (4, 2, 2, 1, '광안리 야경', 'MORNING', FALSE, NOW(), NOW()),
    -- 제주 여행
    (5, 3, 1, 1, '한라산 등반', 'MORNING',FALSE, NOW(), NOW()),
    (6, 3, 2, 1, '성산일출봉 방문', 'MORNING', FALSE, NOW(), NOW()),
    (7, 3, 3, 1, '협재 해수욕장', 'MORNING',FALSE, NOW(), NOW()),
    -- 강릉 여행
    (8, 4, 1, 1, '정동진 일출', 'MORNING',FALSE, NOW(), NOW()),
    (9, 4, 2, 1, '경포대 산책', 'MORNING',FALSE, NOW(), NOW()),
    -- 여수 여행
    (10, 5, 1, 1, '오동도 관광', 'MORNING',FALSE, NOW(), NOW()),
    (11, 5, 2, 1, '돌산대교 야경', 'MORNING', FALSE, NOW(), NOW());

-- =====================================================
-- 6. Place
-- =====================================================
INSERT INTO place (id, name, link, address, latitude, longitude, place_category, schedule_id, created_at, updated_at)
VALUES
    -- 서울 여행
    (1, '경복궁', 'https://gyeongbokgung.or.kr', '서울 종로구 사직로 161', 37.579617, 126.977041, 'TOURIST_ATTRACTION', 1, NOW(), NOW()),
    (2, '남산타워', 'https://nseoultower.co.kr', '서울 용산구 남산공원길 105', 37.551169, 126.988227, 'TOURIST_ATTRACTION', 2, NOW(), NOW()),
    -- 부산 여행
    (3, '해운대', 'https://www.haeundae.go.kr', '부산 해운대구', 35.1587, 129.1604, 'TOURIST_ATTRACTION', 3, NOW(), NOW()),
    (4, '광안리', 'https://www.gwangalli.go.kr', '부산 수영구', 35.1535, 129.1187, 'TOURIST_ATTRACTION', 4, NOW(), NOW()),
    -- 제주 여행
    (5, '한라산', 'https://www.hallasan.go.kr', '제주 한라산', 33.3617, 126.5292, 'TOURIST_ATTRACTION', 5, NOW(), NOW()),
    (6, '성산일출봉', 'https://www.jeju.go.kr', '제주 성산', 33.4581, 126.9410, 'TOURIST_ATTRACTION', 6, NOW(), NOW()),
    (7, '협재 해수욕장', 'https://www.jeju.go.kr', '제주 협재', 33.2479, 126.2395, 'TOURIST_ATTRACTION', 7, NOW(), NOW()),
    -- 강릉 여행
    (8, '정동진', 'https://www.jeongdongjin.go.kr', '강릉 정동진', 37.7566, 129.1133, 'TOURIST_ATTRACTION', 8, NOW(), NOW()),
    (9, '경포대', 'https://www.gangneung.go.kr', '강릉 경포', 37.7519, 128.8955, 'TOURIST_ATTRACTION', 9, NOW(), NOW()),
    -- 여수 여행
    (10, '오동도', 'https://www.yeosu.go.kr', '여수 오동도', 34.7405, 127.7212, 'TOURIST_ATTRACTION', 10, NOW(), NOW()),
    (11, '돌산대교', 'https://www.yeosu.go.kr', '여수 돌산대교', 34.7291, 127.7576, 'TOURIST_ATTRACTION', 11, NOW(), NOW());
-- =====================================================
-- 7. Album
-- =====================================================
INSERT INTO album (id, trip_id, title, created_at, updated_at)
VALUES
    (1, 1, '서울 여행 앨범', NOW(), NOW()),
    (2, 2, '부산 여행 앨범', NOW(), NOW()),
    (3, 3, '제주 여행 앨범', NOW(), NOW()),
    (4, 4, '강릉 여행 앨범', NOW(), NOW()),
    (5, 5, '여수 여행 앨범', NOW(), NOW());

-- =====================================================
-- 8. Photo
-- =====================================================
INSERT INTO photo (id, trip_id, album_id, uploader_id, original_file_name, content_type, file_size, s3key, created_at, updated_at)
VALUES
    -- 서울
    (1, 1, 1, 2, 'IMG_01.JPG', 'image/jpeg', 2301234,'trips/1/photos/photo1.png', NOW(), NOW()),
    (2, 1, 1, 2, 'IMG_02.JPG', 'image/jpeg', 2301234,'trips/1/photos/photo2.png', NOW(), NOW()),
    -- 부산
    (3, 2, 2, 1, 'IMG_03.JPG', 'image/jpeg', 2301234,'trips/2/photos/photo3.png', NOW(), NOW()),
    (4, 2, 2, 1, 'IMG_04.JPG', 'image/jpeg', 2301234,'trips/2/photos/photo4.png', NOW(), NOW()),
    -- 제주
    (5, 3, 3, 7, 'IMG_05.JPG', 'image/jpeg', 2301234,'trips/3/photos/photo5.png', NOW(), NOW()),
    (6, 3, 3, 7, 'IMG_06.JPG', 'image/jpeg', 2301234,'trips/3/photos/photo6.png', NOW(), NOW()),
    (7, 3, 3, 7, 'IMG_07.JPG', 'image/jpeg', 2301234,'trips/3/photos/photo7.png', NOW(), NOW()),
    -- 강릉
    (8, 4, 4, 10, 'IMG_08.JPG', 'image/jpeg', 2301234,'trips/4/photos/photo8.png', NOW(), NOW()),
    (9, 4, 4, 10, 'IMG_09.JPG', 'image/jpeg', 2301234,'trips/4/photos/photo9.png', NOW(), NOW()),
    -- 여수
    (10, 5, 5, 2, 'IMG_10.JPG', 'image/jpeg', 2301234,'trips/5/photos/photo10.png', NOW(), NOW()),
    (11, 5, 5, 2, 'IMG_11.JPG', 'image/jpeg', 2301234,'trips/5/photos/photo11.png', NOW(), NOW());

-- =====================================================
-- 9. Member
-- =====================================================
INSERT INTO member (id, user_id, trip_id, member_role, member_status, created_at, updated_at)
VALUES
    -- 서울 여행 (owner: user2)
    (1, 2, 1, 'OWNER', 'ACCEPTED', NOW(), NOW()),
    (2, 1, 1, 'EDITOR', 'ACCEPTED', NOW(), NOW()),
    (3, 3, 1, 'VIEWER', 'ACCEPTED', NOW(), NOW()),
    -- 부산 여행 (owner: user1)
    (4, 1, 2, 'OWNER', 'ACCEPTED', NOW(), NOW()),
    (5, 4, 2, 'EDITOR', 'ACCEPTED', NOW(), NOW()),
    (6, 5, 2, 'VIEWER', 'ACCEPTED', NOW(), NOW()),
    -- 제주 여행 (owner: user7)
    (7, 7, 3, 'OWNER', 'ACCEPTED', NOW(), NOW()),
    (8, 8, 3, 'EDITOR', 'ACCEPTED', NOW(), NOW()),
    (9, 9, 3, 'VIEWER', 'ACCEPTED', NOW(), NOW()),
    -- 강릉 여행 (owner: user10)
    (10, 10, 4, 'OWNER', 'ACCEPTED', NOW(), NOW()),
    (11, 1, 4, 'EDITOR', 'ACCEPTED', NOW(), NOW()),
    -- 여수 여행 (owner: user2)
    (12, 2, 5, 'OWNER', 'ACCEPTED', NOW(), NOW()),
    (13, 3, 5, 'VIEWER', 'ACCEPTED', NOW(), NOW());