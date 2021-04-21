create table car(
    id serial primary key,
    name varchar,
    id_engine int not null references engine(id),
    id_driver int references driver(id));

create table engine(
    id serial primary key,
    name varchar unique not null);

create table driver(
    id serial primary key,
    name varchar unique not null);

create table history_owner(
    id serial primary key,
    id_driver int references driver(id),
    id_car int references car(id));

