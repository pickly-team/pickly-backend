INSERT INTO member
(username, password, is_hard_mode, email, name, nickname, profile_emoji, fcm_token)
VALUES ('test', 'helpme', true, 'test@gmail.com', '테스트1', '테스트1', '😎', null),
       ('test2', 'ohmygod', false, 'test2@gmail.com', '테스트2', '테스트2', '😳', null);

INSERT INTO category
    (member_id, is_auto_delete_mode, name, emoji)
VALUES (1, true, '백엔드', '😎'),
       (1, false, '프론트엔드', '😳');

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
       (2, 1, true);

INSERT INTO notification_standard
    (member_id, standard_date, is_active)
VALUES (1, 7, true),
       (2, 3, false);

INSERT INTO notifiaction
    (member_id, title, message, is_checked, notification_type)
VALUES (1, '테스트 알림', '테스트입니다', false, 1),
       (1, '테스트 알림2', '이것도 테스트에요', true, 1),
       (2, '안녕안녕', '마이크테스트 아아', true, 1);