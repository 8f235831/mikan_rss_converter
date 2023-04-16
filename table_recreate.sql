drop table if exists `rss_follows`;
create table `rss_follows`
(
    id                           Integer      not null
        constraint rss_follows_pk primary key autoincrement,
    rss_site                     VARCHAR(255) not null,
    regex_filter                 VARCHAR(255) default '' not null,
    comment                      VARCHAR(255) default '' not null,
    enabled                      SMALLINT     default 1 not null,
    follow_added_time            Integer      default (strftime('%s', 'now')) not null,
    follow_modified_time         Integer      default (strftime('%s', 'now')) not null,
    last_update_succeed_time     Integer      default 0 not null,
    update_continue_fail_counter Integer      default 0 not null
);

create unique index rss_follows_id_uindex
    on rss_follows (id);

insert into rss_follows(rss_site, regex_filter, comment)
values ('https://mikanani.me/RSS/Bangumi?bangumiId=2966&subgroupid=370', '', '和山田谈场Lv999的恋爱'),
       ('https://mikanani.me/RSS/Bangumi?bangumiId=3024&subgroupid=615', 'sentai', '可爱过头大危机'),
       ('http://example.com/2', '2', 'example_2');