create table author(
    id serial primary key,
    name varchar);

create table book(
    id serial primary key,
    name varchar);

create table author_book(
    id_author int references author(id),
    id_book int references book(id));
drop table author_book;

select * from author;
select * from book;