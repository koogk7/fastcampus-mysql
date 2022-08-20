create table member
(
    id int auto_increment,
    email varchar(20) not null,
    nickname varchar(20) not null,
    birthday date not null,
    createdAt datetime not null,
    constraint member_id_uindex
        primary key (id)
);


-- 마이그레이션은 뒷 챕터에 다룸
alter table member add companyCode int default -1 not null;



