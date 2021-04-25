create table database_vacancy
(
    id   serial primary key,
    name varchar not null unique
);

create table candidate
(
    id                  serial primary key,
    name                varchar(50) not null,
    experience          smallint,
    salary              smallint,
    id_database_vacancy int unique references database_vacancy (id)
);

create table vacancy
(
    id                  serial primary key,
    description         text not null,
    id_database_vacancy int references database_vacancy (id)
);
