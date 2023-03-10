INSERT INTO member
    (username, password, is_hard_mode, email, name, nickname, profile_emoji)
VALUES
    ('test', 'helpme', true, 'test@gmail.com', '테스트1', '테스트1', '😎'),
    ('test2', 'ohmygod', false, 'test2@gmail.com', '테스트2', '테스트2', '😳');

INSERT INTO category
(member_id, is_auto_delete_mode, name, emoji)
VALUES
    (1, true, '백엔드', '😎'),
    (1, false, '프론트엔드', '😳');

INSERT INTO bookmark
(category_id, member_id, url, title, preview_image_url, is_user_like, read_by_user, visibility)
VALUES
    (1, 1, 'https://naver.com', '스프링 쌈싸먹기', null, false, false, 'SCOPE_PUBLIC'),
    (2, 1, 'https://google.com', '리액트 쌈싸먹기', null, true, true, 'SCOPE_PRIVATE'),
    (1, 1, 'https://naver.com1', '스프링 쌈싸먹기1', null, true, true, 'SCOPE_PUBLIC'),
    (2, 1, 'https://google.com1', '리액트 쌈싸먹기1', null, true, false, 'SCOPE_PRIVATE'),
    (1, 1, 'https://google.com2', '리액트 쌈싸먹기2', null, true, true, 'SCOPE_PRIVATE'),
    (1, 1, 'https://google.com2', '리액트 쌈싸먹기2', null, true, false, 'SCOPE_PRIVATE');

INSERT INTO comment
(member_id, bookmark_id, is_owner_comment, content)
VALUES
    (1, 1, true, '정말 완벽한 글이에요'),
    (2, 1, false, '너 아직도 백엔드하니?');

INSERT INTO friend
(followee_id, follower_id)
VALUES
    (1, 2),
    (2, 1);