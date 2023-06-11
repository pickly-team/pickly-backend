INSERT INTO member
(username, password, is_hard_mode, email, name, nickname, profile_emoji, fcm_token)
VALUES ('test', 'helpme', true, 'test@gmail.com', '테스트1', '테스트1', '😎', null),
       ('test2', 'ohmygod', false, 'test2@gmail.com', '테스트2', '테스트2', '😳', null),
       ('test3', 'ohmygod', false, 'test3@gmail.com', '테스트3', '테스트3', '🫥', null),
       ('test4', 'ohmygod', false, 'test4@gmail.com', '테스트4', '테스트4', '😪', null);

INSERT INTO block
(blockee_id, blocker_id, bookmark_id)
VALUES (3, 1, null);

INSERT INTO category
    (member_id, name, order_num, emoji)
VALUES (1, '백엔드', 1, '😎'),
       (1, '프론트엔드', 2, '😳');

INSERT INTO bookmark
(category_id, member_id, url, title, preview_image_url, is_user_like, read_by_user, visibility)
VALUES (1, 1, 'https://naver.com', '스프링 쌈싸먹기', null, false, false, 'SCOPE_PUBLIC'),
       (2, 1, 'https://google.com', '리액트 쌈싸먹기', null, true, true, 'SCOPE_PRIVATE'),
       (1, 1, 'https://naver.com1', '스프링 쌈싸먹기1', null, true, true, 'SCOPE_PUBLIC'),
       (2, 1, 'https://google.com1', '리액트 쌈싸먹기1', null, true, false, 'SCOPE_PRIVATE'),
       (1, 1, 'https://google.com2', '리액트 쌈싸먹기2', null, true, true, 'SCOPE_PRIVATE'),
       (1, 1, 'https://google.com2', '리액트 쌈싸먹기2', null, true, false, 'SCOPE_PRIVATE');

INSERT INTO comment
    (member_id, bookmark_id, is_owner_comment, content)
VALUES (1, 1, true, '정말 완벽한 글이에요'),
       (2, 1, false, '너 아직도 백엔드하니?');

INSERT INTO friend
    (followee_id, follower_id, notification_enabled)
VALUES (1, 2, false),
       (2, 1, true),
       (1, 3, false),
       (1, 4, false);

-- notify_daily_at is  time without timezone
INSERT INTO notification_standard
    (member_id, notify_standard_day, notify_daily_at, is_active)
VALUES (1, 3, '09:00:00', true),
       (2, 7, '18:00:00', false);

INSERT INTO notification
    (member_id, bookmark_id, title, content, is_checked, is_send, send_date_time, notification_type)
VALUES (1, 1, '테스트 알림', '테스트입니다', false, true, '2023-06-07 11:30:00', 1),
       (1, 1, '테스트 알림2', '이것도 테스트에요', true, true, '2023-06-07 11:30:00', 1);
