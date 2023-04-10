drop table if exists friend;
drop table if exists comment;
drop table if exists bookmark;
drop table if exists category;
drop table if exists notification_standard;
drop table if exists notifiaction;
drop table if exists member;

CREATE
OR
REPLACE FUNCTION updated_at()
RETURNS TRIGGER AS '
BEGIN
  NEW.updated_at = NOW();
  RETURN NEW;
END;
' LANGUAGE plpgsql;

create table member
(
    id bigserial
        constraint member_pk
        primary key,
    username      varchar(80)             not null,
    password      varchar(80)             not null,
    is_hard_mode  boolean                 not null,
    email         varchar(100)            not null,
    name          varchar(20)             not null,
    nickname      varchar(20)             not null,
    profile_emoji text,
    fcm_token     varchar(200),
    created_at    timestamp default now() not null,
    updated_at    timestamp,
    deleted_at    timestamp
);

create unique index member_email_uindex
    on member (email);

create unique index member_nickname_uindex
    on member (nickname);

create unique index member_username_uindex
    on member (username);

create trigger update_trigger
    before update
    on member
    for each row
    execute procedure updated_at();

create table category
(
    id bigserial
        constraint category_pk
        primary key,
    member_id           bigint                  not null
        constraint category_member_id_fk
        references member
        on update cascade on delete cascade,
    is_auto_delete_mode boolean                 not null,
    name                varchar(100)            not null,
    emoji               text,
    created_at          timestamp default now() not null,
    updated_at          timestamp,
    deleted_at          timestamp
);

create trigger update_trigger
    before update
    on category
    for each row
    execute procedure updated_at();

create table bookmark
(
    id bigserial
        constraint bookmark_pk
        primary key,
    category_id       bigint                  not null
        constraint bookmark_category_id_fk
        references category
        on update cascade on delete cascade,
    member_id         bigint                  not null
        constraint bookmark_member_id_fk
        references member
        on update cascade on delete cascade,
    url               varchar(500)            not null,
    title             varchar(100)            not null,
    preview_image_url varchar(500),
    is_user_like      boolean                 not null,
    read_by_user      boolean                 not null,
    visibility        varchar(50),
    created_at        timestamp default now() not null,
    updated_at        timestamp,
    deleted_at        timestamp
);

create trigger update_trigger
    before update
    on bookmark
    for each row
    execute procedure updated_at();

create table comment
(
    id bigserial
        constraint comment_pk
        primary key,
    member_id        bigint                  not null
        constraint comment_member_id_fk
        references member
        on update cascade on delete cascade,
    bookmark_id      bigint                  not null
        constraint comment_bookmark_id_fk
        references bookmark
        on update cascade on delete cascade,
    is_owner_comment boolean                 not null,
    content          varchar(150)            not null,
    created_at       timestamp default now() not null,
    updated_at       timestamp,
    deleted_at       timestamp
);

create trigger update_trigger
    before update
    on comment
    for each row
    execute procedure updated_at();

create table friend
(
    id bigserial
        constraint friend_pk
        primary key,
    followee_id          bigint                  not null
        constraint friend_member_id_fk
        references member
        on update cascade on delete cascade,
    follower_id          bigint                  not null
        constraint friend_member_id_fk_2
        references member
        on update cascade on delete cascade,
    notification_enabled boolean                 not null,
    created_at           timestamp default now() not null,
    updated_at           timestamp,
    deleted_at           timestamp
);

create trigger update_trigger
    before update
    on friend
    for each row
    execute procedure updated_at();

create table notification_standard
(
    id bigserial
        constraint notification_standard_pk
        primary key,
    member_id     bigint                  not null
        constraint bookmark_member_id_fk
        references member
        on update cascade on delete cascade,
    standard_date integer   default 7     not null,
    is_active     boolean                 not null,
    created_at    timestamp default now() not null,
    updated_at    timestamp,
    deleted_at    timestamp
);

create trigger update_trigger
    before update
    on notification_standard
    for each row
    execute procedure updated_at();

create table notification
(
    id bigserial
        constraint notification_pk
        primary key,
    member_id         bigint                  not null,
    title             varchar(255)            not null,
    content           varchar(255)            not null,
    is_checked        boolean                 not null,
    notification_type integer                 not null,
    created_at        timestamp default now() not null,
    updated_at        timestamp,
    deleted_at        timestamp
);

create trigger update_trigger
    before update
    on notification
    for each row
    execute procedure updated_at();


create table notification_template
(
    id bigserial
        constraint notification_pk
        primary key,
    title             varchar(255)            NOT NULL,
    content           varchar(255)            NOT NULL,
    notification_type integer                 not null,
    created_at        timestamp default now() not null,
    updated_at        timestamp,
    deleted_at        timestamp
);

create trigger update_trigger
    before update
    on notification_template
    for each row
    execute procedure updated_at();