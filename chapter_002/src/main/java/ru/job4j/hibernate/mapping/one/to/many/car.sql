create table car_brand(
    id serial primary key,
    name varchar);

create table car_model(
    id serial primary key,
    name varchar,
    id_brand int references car_brand(id));

select * from car_brand;
select * from car_model;
select * from car_brand_car_model;